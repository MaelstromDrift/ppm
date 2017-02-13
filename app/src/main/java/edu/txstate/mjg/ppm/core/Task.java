package edu.txstate.mjg.ppm.core;

import android.icu.util.Calendar;

public class Task {
    private int taskId;
    private String title;
    private String description;
    public boolean completed;
    public Calendar completionDate; //TODO: Learn how to store date

    public Task() {
        this.taskId = 0;
        this.title = "Example Task";
        this.description = "This is an example task";
        this.completed = false;
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

    public boolean getCompletionStatus() {
        return completed;
    }
}
