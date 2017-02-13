package edu.txstate.mjg.ppm.core;

public class Task {

    /*
    taskId: the unique integer identifier created for this task
    title: The official title of the task.
    Description: information regarding this particular task. (how to complete, required information)
    state: the status of the current task (not-started / in-progress / completed)
    creatorID: The creator of this specific task. (changes on modification of the task to the modifying user)

    startTimeStamp: the time when the task was started  (mm/dd/yyyy hh:mm)
    completionTimeStamp: the time when the task was finished (mm/dd/yyyy hh:mm)
     */

    enum TaskStates {
        notStarted,
        inProgress,
        completed
    }

    private int        taskId;
    private String     title;
    private String     description;
    private TaskStates state;
    private int        creatorId;

    /*
    TODO: Learn how to store a timestamp properly
    private time    startTimestamp;
    private time    completionTimestamp;
     */

    public Task() {
        this.taskId = 0;
        this.title = "Example Task";
        this.description = "This is an example task";
        this.creatorId = 0;
        this.state = TaskStates.notStarted;
    }

    public int getTaskId() {
        return taskId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getCreatorID() { return creatorId; }

    public void setCreatorID(int userID) { creatorId = userID; }

    public TaskStates getCompletionStatus() { return state; }
}
