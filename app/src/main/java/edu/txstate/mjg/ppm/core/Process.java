package edu.txstate.mjg.ppm.core;

import java.util.ArrayList;

public class Process {

    //TODO; confirm that these are the three states that the process can be in at any time.
    public enum ProcessState {
        planning,
        inProgress,
        completed
    }

    //TODO: think of better way to manage categories.
    public enum Categories{
        lifestyle,                  //0
        fitness,                    //1
        education                   //2
    }

    /*
    Class Member Variables:
        uniqueID:       the unique id created for the specific task.
        creatorID:      the unique ID of the creator/modifier of this process
        title:          The official name for the process.
        description:    A description of what this process aims to help accomplish
        taskList:       The list of tasks associated with this process
        category:       The category that the process belongs to.
        state:          The current state of the process
    */

    private int uniqueID;
    private int creatorID;
    private String title;
    private String description;
    private ArrayList<Task> taskList; //TODO: work on a more suitable way to structure the tasks in order to conform with the tree like structure described in the paper.
    private Categories category;

    private ProcessState state;

    public Process () {
        uniqueID    = 0;
        creatorID   = 0;
        title       = "Example";
        description = "This is a test description";
        taskList    = new ArrayList<Task>();
        category    = Categories.lifestyle;
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
    public int numTasks() {
        return taskList.size();
    }
    public Categories getCategory() {
        return category;
    }

    public void setCategory(Categories category) { this.category = category; }
    public void setTitle(String title) { this.title = title; }
}
