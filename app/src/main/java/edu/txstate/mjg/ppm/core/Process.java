package edu.txstate.mjg.ppm.core;

import java.util.ArrayList;

public class Process {

    //TODO: Create the Process data structure to follow the tree format shown in the paper.
    //TODO; confirm that these are the three states that the process can be in at any time.
    public enum ProcessState {
        PROCESS_PLANNING,
        PROCESS_INPROGRESS,
        PROCESS_COMPLETED
    }

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
        state:          The current state of the process
    */

    private int uniqueID;
    private int creatorID;
    private String title;
    private String description;
    private ArrayList<Task> taskList;
    private Categories category;

    private ProcessState state;

    public Process () {
        uniqueID    = 0;
        creatorID   = 0;
        title       = "Example";
        description = "This is a test description";
        taskList    = new ArrayList<>();
        category    = Categories.Lifestyle;
        state       = ProcessState.PROCESS_PLANNING;
    }

    public Process(String title, String description, String category, int creatorID) {
        this.title = title;
        this.description = description;
        this.category = Categories.valueOf(category);
        this.creatorID = creatorID;

        uniqueID    = 0;
        creatorID   = 0;

        taskList    = new ArrayList<>();
        state       = ProcessState.PROCESS_PLANNING;
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

    public String getCsvTasks() {
        String tasksString = "";
        if(taskList == null || taskList.isEmpty()) {
            return tasksString;
        } else {
            for (Task t : taskList) {
                if (tasksString.equals(""))
                    tasksString = Integer.toString(t.getTaskId());

                tasksString = tasksString + "," + Integer.toString(t.getTaskId());
            }
        }
        return tasksString;
    }

    public Categories getCategory() {
        return category;
    }
    public ProcessState getState() {return state;}

    public void setState(ProcessState state) { this.state = state; }
    public void setCategory(Categories category) { this.category = category; }
    public void setTitle(String title) { this.title = title; }
    public void setDescription(String text) {description = text;}
}
