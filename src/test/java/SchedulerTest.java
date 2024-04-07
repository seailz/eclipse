import com.seailz.eclipse.EclipseScheduler;
import com.seailz.eclipse.model.Task;

public class SchedulerTest {

    public static void main(String[] args) {
        EclipseScheduler.scheduleTask(new Task(
                "task1",
                () -> System.out.println("Hello, world!"),
                10000,
                true,
                Task.TaskSettings.DEFAULT
        ));
    }

}
