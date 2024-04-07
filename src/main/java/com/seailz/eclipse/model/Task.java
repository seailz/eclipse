package com.seailz.eclipse.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * Represents a task that can be scheduled.
 */
@Getter
@RequiredArgsConstructor
public class Task {

    private final String id;
    private final Runnable runnable;
    private final long delayMs;
    private final boolean repeating;
    private final TaskSettings settings;

    @Setter
    @Getter
    @AllArgsConstructor
    public static class TaskSettings {

        public static final TaskSettings DEFAULT = new TaskSettings(
                true
        );

        /**
         * If true, the task will be executed even if the previous execution took longer than the delay, or the task's next execution time was missed.
         */
        private boolean missedRedundancy;

    }

}
