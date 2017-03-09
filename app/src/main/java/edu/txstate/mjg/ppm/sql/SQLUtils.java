package edu.txstate.mjg.ppm.sql;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import edu.txstate.mjg.ppm.core.Process;
import edu.txstate.mjg.ppm.core.Task;
import edu.txstate.mjg.ppm.sql.PPMDatabaseContract.ProcessEntry;
import edu.txstate.mjg.ppm.sql.PPMDatabaseContract.TaskEntry;
/**
 * Created by MarkGitthens on 3/5/2017.
 */

/**
 * Need to be able to do these operations on the DB
 * 1: Check for process/task existence
 * 2: insert new process/tasks
 * 3: update existing process/tasks
 * 4: delete processes/tasks
 */
public class SQLUtils {

     public static void insertProcess(SQLiteDatabase db, Process process) {
         ContentValues values = new ContentValues();


         values.put(ProcessEntry.COLUMN_PROCESS_TITLE, process.getTitle());
         values.put(ProcessEntry.COLUMN_PROCESS_DESCRIPTION, process.getDescription());
         values.put(ProcessEntry.COLUMN_PROCESS_CATEGORY, process.getCategory().name());
         values.put(ProcessEntry.COLUMN_PROCESS_CREATOR_ID, process.getCreatorID());
         values.put(ProcessEntry.COLUMN_PROCESS_LINKED_TASKS, process.getCsvTasks());

         db.insert(ProcessEntry.TABLE_NAME, null, values);

         //if Exists in DB
        //  if canEdit
        //      update the row
        //  else
        //      create new row
        //else
        //  create new row
    }

    public static Boolean exists(SQLiteDatabase db, int uID, int type) {
        if(type == 0)
            return true;
            //check for existence of process
        else if(type == 1)
            return true;
            //check for existence of task
        else
            return false;
            //default return false;
    }
    public static Process getProcess(SQLiteDatabase db, int processID) {
        String[] projection = {
                ProcessEntry.COLUMN_PROCESS_LINKED_TASKS,
                ProcessEntry.COLUMN_PROCESS_CREATOR_ID,
                ProcessEntry.COLUMN_PROCESS_DESCRIPTION,
                ProcessEntry.COLUMN_PROCESS_TITLE,
                ProcessEntry._ID
        };

        String whereClause = "_id = ?";

        String[] whereArgs = new String[] {
                Integer.toString(processID)
        };

        Cursor cursor = db.query(ProcessEntry.TABLE_NAME, projection, whereClause, whereArgs, null, null, null);
        List<Process> processes = new ArrayList<>();

        //TODO: can this be done without creating arraylist and looping through it?
        while(cursor.moveToNext()) {
            //TODO: Get linked tasks
            processes.add(new Process(cursor.getInt(cursor.getColumnIndexOrThrow(ProcessEntry._ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(ProcessEntry.COLUMN_PROCESS_TITLE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(ProcessEntry.COLUMN_PROCESS_DESCRIPTION)),
                    cursor.getString(cursor.getColumnIndexOrThrow(ProcessEntry.COLUMN_PROCESS_CATEGORY)),
                    parseCSVTasks(db, cursor.getString(cursor.getColumnIndexOrThrow(ProcessEntry.COLUMN_PROCESS_LINKED_TASKS))),
                    cursor.getInt(cursor.getColumnIndexOrThrow(ProcessEntry.COLUMN_PROCESS_CREATOR_ID))
            ));
        }
        cursor.close();

        return processes.get(0);
    }

    //TODO: Implement error handling when task doesn't exist
    public static Task getTask(SQLiteDatabase db, int taskID) {
        String[] projection = {
                TaskEntry.COLUMN_TASK_LINKED_TASKS,
                TaskEntry.COLUMN_TASK_CREATOR_ID,
                TaskEntry.COLUMN_TASK_DESCRIPTION,
                TaskEntry.COLUMN_TASK_TITLE,
                TaskEntry._ID
        };

        String whereClause = "_id = ?";

        String[] whereArgs = new String[] {
                Integer.toString(taskID)
        };

        Cursor cursor = db.query(TaskEntry.TABLE_NAME, projection, whereClause, whereArgs, null, null, null);
        List<Task> tasks = new ArrayList<>();

        while(cursor.moveToNext()) {
            //TODO: Get linked tasks
            tasks.add(new Task(cursor.getInt(cursor.getColumnIndexOrThrow(TaskEntry._ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(TaskEntry.COLUMN_TASK_TITLE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(TaskEntry.COLUMN_TASK_DESCRIPTION)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(TaskEntry.COLUMN_TASK_CREATOR_ID))
            ));
        }
        cursor.close();

        return tasks.get(0);
    }
    public static ArrayList<Process> getAllProcesses(SQLiteDatabase db) {
        //Decide which columns we want to get
        String[] projection = {
            ProcessEntry._ID,
            ProcessEntry.COLUMN_PROCESS_TITLE,
            ProcessEntry.COLUMN_PROCESS_DESCRIPTION,
            ProcessEntry.COLUMN_PROCESS_CATEGORY,
            ProcessEntry.COLUMN_PROCESS_LINKED_TASKS,
            ProcessEntry.COLUMN_PROCESS_CREATOR_ID
        };

        //Query the database for all entries
        Cursor cursor = db.query(ProcessEntry.TABLE_NAME, projection, null, null, null, null, null);

        ArrayList<Process> processes = new ArrayList<>();
        while(cursor.moveToNext()) {
            processes.add(new Process(cursor.getInt(cursor.getColumnIndexOrThrow(ProcessEntry._ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(ProcessEntry.COLUMN_PROCESS_TITLE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(ProcessEntry.COLUMN_PROCESS_DESCRIPTION)),
                    cursor.getString(cursor.getColumnIndexOrThrow(ProcessEntry.COLUMN_PROCESS_CATEGORY)),
                    parseCSVTasks(db, cursor.getString(cursor.getColumnIndexOrThrow(ProcessEntry.COLUMN_PROCESS_LINKED_TASKS))),
                    cursor.getInt(cursor.getColumnIndexOrThrow(ProcessEntry.COLUMN_PROCESS_CREATOR_ID))));
        }
        cursor.close();
        return processes;
    }

    public static Process[] getUsersProcesses(SQLiteDatabase db, int userID) {
        return null;
    }



    public static void updateTask(SQLiteDatabase db, Task task) {

    }

    private static ArrayList<Task> parseCSVTasks(SQLiteDatabase db, String csvTasks) {
        String[] parsedString = csvTasks.split(",");
        ArrayList<Task> tasks = new ArrayList<>();
        for(String s: parsedString) {
            tasks.add(SQLUtils.getTask(db, Integer.parseInt(s)));
        }
        return tasks;
    }
}
