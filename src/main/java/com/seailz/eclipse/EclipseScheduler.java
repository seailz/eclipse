package com.seailz.eclipse;

import com.seailz.eclipse.model.Task;
import com.seailz.eclipse.storage.FileSystemStorage;
import com.seailz.eclipse.storage.StorageSystem;
import lombok.experimental.UtilityClass;

@UtilityClass
public class EclipseScheduler {

    /**
     * Currently, the only storage system available is the file system. This will change in the future.
     */
    private final StorageSystem storageSystem = new FileSystemStorage();

    /**
     * Schedules a task.
     * <br>If a saved task with the same ID exists, it'll tap into that task and pick up where it left off.
     * @param task The task to schedule
     */
    public void scheduleTask(Task task) {
        new Thread(() -> {
            while (true) {
                try {
                    if (storageSystem.taskExists(task.getId())) {
                        long lastRun = storageSystem.getLastRun(task.getId());
                        long timeSinceLastRun = System.currentTimeMillis() - lastRun;
                        if (timeSinceLastRun < task.getDelayMs()) {
                            Thread.sleep(task.getDelayMs() - timeSinceLastRun);
                        } else {
                            if (!task.getSettings().isMissedRedundancy()) {
                                // Ignore the previous execution. Let's find our next execution time.
                                long newExecutionTime = lastRun;

                                while (newExecutionTime + task.getDelayMs() < System.currentTimeMillis()) {
                                    newExecutionTime += task.getDelayMs();
                                }

                                Thread.sleep(newExecutionTime);
                            }
                        }
                    } else {
                        storageSystem.setLastRun(task.getId(), System.currentTimeMillis()); // Set the last run time to now, even if the task has never been run before.
                        Thread.sleep(task.getDelayMs());
                    }

                    storageSystem.setLastRun(task.getId(), System.currentTimeMillis());

                    new Thread(task.getRunnable(), "eclipsetask_nested-" + task.getId()).start();

                    if (!task.isRepeating()) {
                        storageSystem.removeLastRun(task.getId());
                        break;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "eclipsetask-" + task.getId()).start();
    }

}
