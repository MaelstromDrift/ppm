package edu.txstate.mjg.ppm.core;

import java.util.ArrayList;

public class Process {
    public static enum categories{
        lifestyle,                  //0
        fitness,                    //1
        education                   //2
    };

    private int uniqueID;
    private int userID;
    private String title;
    private String description;
    private ArrayList<Task> taskList;
    private categories category;

    public Process () {
        uniqueID    = 0;
        userID      = 0;
        title       = "Example";
        description = "This is a test description";
        taskList    = new ArrayList<Task>();
        category    = categories.lifestyle;
    }

    public void addTask(Task task) {
        taskList.add(task);
    }

    public int getUniqueID() {
        return uniqueID;
    }
    public int getUserID() {
        return userID;
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
    public categories getCategory() {
        return category;
    }
}
