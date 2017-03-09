package edu.txstate.mjg.ppm.core;

import java.util.List;

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

    private int        taskID;
    private int        creatorId;
    private String     title;
    private String     description;
    private TaskStates state;

    private List<Task> linkedTasks;

    /*
    TODO: Learn how to store a timestamp properly
    private time    startTimestamp;
    private time    completionTimestamp;
     */

    public Task() {
        this.taskID = 0;
        this.title = "Example Task";
        this.description = "This is an example task";
        this.creatorId = 0;
        this.state = TaskStates.notStarted;
    }
    public Task(int taskID, String title, String description, int creatorId) {
        this.taskID = taskID;
        this.title = title;
        this.description = description;
        this.creatorId = creatorId;
        this.state = TaskStates.notStarted;
    }

    //Linked Tasks Operations
    public void addTask(Task task) { linkedTasks.add(task); }

    public void addTask(int order, Task task) { linkedTasks.add(task); }

    public void deleteLinkedTask(int pos) {linkedTasks.remove(pos);}

    public Task getLinkedTask(int pos) {return linkedTasks.get(pos);}

    //General Task Operations
    public int getTaskId() {
        return taskID;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getCreatorID() { return creatorId; }

    public TaskStates getState() { return state; }

    public void setTitle(String title) { this.title = title; }

    public void setDescription(String description) {this.description = description;}

    public void setState(TaskStates state) {this.state = state;}

    public void setCreatorID(int userID) { creatorId = userID; }

    public TaskStates getCompletionStatus() { return state; }
}
