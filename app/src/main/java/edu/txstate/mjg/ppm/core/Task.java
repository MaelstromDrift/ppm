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
    private int        taskID;
    private int        creatorId;
    private String     title;
    private String     description;

    private List<Task> linkedTasks;

    /*
    TODO: Learn how to store a timestamp properly
    private time    startTimestamp;
    private time    completionTimestamp;
     */

    public Task() {
        this.taskID = 1;
        this.title = "Default Task";
        this.description = "This is the default Task!";
        this.creatorId = 0;
    }

    public Task(String title, String description, int creatorId) {
        this.taskID = 1;
        this.title = title;
        this.description = description;
        this.creatorId = creatorId;
    }
    public Task(int taskID, String title, String description, int creatorId) {
        this.taskID = taskID;
        this.title = title;
        this.description = description;
        this.creatorId = creatorId;
    }

    //Linked Tasks Operations
    public void addTask(Task task) { linkedTasks.add(task); }

    public void addTask(Task task, int order) { linkedTasks.add(order, task); }

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


    public void setUniqueId(int uniqueId) { this.taskID = uniqueId; }
    public void setTitle(String title) { this.title = title; }

    public void setDescription(String description) {this.description = description;}

    public void setCreatorID(int userID) { creatorId = userID; }

}
