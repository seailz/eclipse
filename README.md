# Eclipse
Eclipse is a scheduling library to schedule repeated tasks **that persist between restarts.** For example, if I started a task to repeat every 2 hours, and then restarted halfway through, then Eclipse would still run the task at the relevant time (in another hour).
## Usage
It's nice and simple to use! Here's an example of scheduling a task:
```java
EclipseScheduler.scheduleTask(new Task(
     "task1", // task ID
    () -> System.out.println("Hello, world!"), // runnable
    10000, // interval (ms)
    true, // repeating?
    Task.TaskSettings.DEFAULT
));
```
Eclipse uses an ID system to uniquely identify tasks. This means, if you try to schedule a task with the same ID as one that's already saved, it'll "tap into" the existing task and make sure the interval is maintained.
## Install
Find your repo and dependency code for your build system [here](https://jitpack.io/#seailz/eclipse/-SNAPSHOT).
