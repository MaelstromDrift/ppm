package edu.txstate.mjg.ppm.core;

import java.util.ArrayList;


//    TODO: We cannot check for a process completion without having some type of user to track what tasks they have completed.
//    TODO: Because of this we have no reason for the process to track state since it can be reused by other users.

public class Process {

    //TODO: think of better way to manage categories.
    public static enum Categories{
        Lifestyle,
        Fitness,
        Education,
        Misc
    }

    /*
    Class Member Variables:
        uniqueID:       the unique id created for the specific task.
        creatorID:      the unique ID of the creator/modifier of this process
        title:          The official name for the process.
        description:    A description of what this process aims to help accomplish
        taskList:       The list of tasks associated with this process
        category:       The category that the process belongs to.
    */

    private int uniqueID;
    private int creatorID;
    private String title;
    private String description;
    private ArrayList<Task> taskList;
    private Categories category;

    public Process() {
        taskList = new ArrayList<>();
        taskList.add(new Task());

        uniqueID = 0;
        creatorID = 0;
    }
    public Process(String title, String description, String category, int creatorID) {
        this.title = title;
        this.description = description;
        this.category = Categories.valueOf(category);
        this.creatorID = creatorID;

        taskList = new ArrayList<>();
        taskList.add(new Task());
        uniqueID = 0;
    }

    public Process(int uniqueID, String title, String description, String category, ArrayList<Task> linkedTasks, int creatorID) {
        this.title = title;
        this.description = description;
        this.category = Categories.valueOf(category);
        this.creatorID = creatorID;

        this.uniqueID = uniqueID;

        taskList    = linkedTasks;
    }


    //TODO: Implement the core functionality of the process. Insert, Delete, Update, + helper functions
    public void addTask(Task task) {
        taskList.add(task);
    }

    public int getUniqueID() {
        return uniqueID;
    }
    public int getCreatorID() {
        return creatorID;
    }
    public String getTitle() {
        return title;
    }
    public String getDescription() {
        return description;
    }

    public String getCsvTasks() {
        String tasksString = "";
        if(taskList == null || taskList.isEmpty()) {
            return tasksString;
        } else {
            for (Task t : taskList) {
                if (tasksString.equals(""))
                    tasksString = Integer.toString(t.getTaskId());
                else
                    tasksString = tasksString + "," + Integer.toString(t.getTaskId());
            }
        }
        return tasksString;
    }

    public ArrayList<Task> getTasks() {
        return taskList;
    }
    public Categories getCategory() {
        return category;
    }

    public void setTitle(String title) { this.title = title; }
    public void setDescription(String description) { this.description = description;}
    public void setCategory(String category) { this.category = Categories.valueOf(category); }
}
