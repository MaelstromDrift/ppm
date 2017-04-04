package edu.txstate.mjg.ppm.utils;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import edu.txstate.mjg.ppm.core.Process;
import edu.txstate.mjg.ppm.core.Task;
import edu.txstate.mjg.ppm.sql.PPMDatabaseContract.ProcessEntry;
import edu.txstate.mjg.ppm.sql.PPMDatabaseContract.ProcessTaskEntry;
import edu.txstate.mjg.ppm.sql.PPMDatabaseContract.TaskEntry;
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

         long processID = db.insert(ProcessEntry.TABLE_NAME, null, values);

         //TODO: should insert the tasks the the DB here, not when the task is originally created
         for(int i = 0; i < process.getTasks().size(); i++) {
             values.clear();
             values.put(ProcessTaskEntry.COLUMN_PROCESS, (int)processID);
             values.put(ProcessTaskEntry.COLUMN_TASK, process.getTasks().get(i).getTaskId());
             values.put(ProcessTaskEntry.COLUMN_ORDER, i);
             db.insert(ProcessTaskEntry.TABLE_NAME, null, values);
         }

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
                ProcessEntry.COLUMN_PROCESS_CREATOR_ID,
                ProcessEntry.COLUMN_PROCESS_DESCRIPTION,
                ProcessEntry.COLUMN_PROCESS_TITLE,
                ProcessEntry.COLUMN_PROCESS_CATEGORY,
                ProcessEntry._ID
        };

        String whereClause = "_id = ?";

        String[] whereArgs = new String[] {
                Integer.toString(processID)
        };

        Cursor cursor = db.query(ProcessEntry.TABLE_NAME, projection, whereClause, whereArgs, null, null, null);
        Process process = new Process();

        cursor.moveToNext();

        process.setUniqueID(cursor.getInt(cursor.getColumnIndexOrThrow(ProcessEntry._ID)));

        process.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(ProcessEntry.COLUMN_PROCESS_TITLE)));
        process.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(ProcessEntry.COLUMN_PROCESS_DESCRIPTION)));
        process.setCategory(cursor.getString(cursor.getColumnIndexOrThrow(ProcessEntry.COLUMN_PROCESS_CATEGORY)));
        process.setCreatorID(cursor.getInt(cursor.getColumnIndexOrThrow(ProcessEntry.COLUMN_PROCESS_CREATOR_ID)));

        for(Task t: getFollowedTasks(db, process.getUniqueID()))
            process.addTask(t);

        cursor.close();

        return process;
    }

    //Returns the UID of the inserted process
    public static long insertTask(SQLiteDatabase db, Task task) {
        ContentValues values = new ContentValues();

        values.put(TaskEntry.COLUMN_TASK_TITLE, task.getTitle());
        values.put(TaskEntry.COLUMN_TASK_DESCRIPTION, task.getDescription());
        values.put(TaskEntry.COLUMN_TASK_CREATOR_ID, task.getCreatorID());

        return db.insert(TaskEntry.TABLE_NAME, null, values);
    }
    //TODO: Implement error handling when task doesn't exist
    public static Task getTask(SQLiteDatabase db, int taskID) {
        String[] projection = {
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
            ProcessEntry.COLUMN_PROCESS_CREATOR_ID
        };

        //Query the database for all entries
        Cursor cursor = db.query(ProcessEntry.TABLE_NAME, projection, null, null, null, null, null);

        ArrayList<Process> processes = new ArrayList<>();
        while(cursor.moveToNext()) {
            Process process = new Process();

            process.setUniqueID(cursor.getInt(cursor.getColumnIndexOrThrow(ProcessEntry._ID)));
            process.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(ProcessEntry.COLUMN_PROCESS_TITLE)));
            process.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(ProcessEntry.COLUMN_PROCESS_DESCRIPTION)));
            process.setCategory(cursor.getString(cursor.getColumnIndexOrThrow(ProcessEntry.COLUMN_PROCESS_CATEGORY)));
            process.setCreatorID(cursor.getInt(cursor.getColumnIndexOrThrow(ProcessEntry.COLUMN_PROCESS_CREATOR_ID)));

            for(Task t: getFollowedTasks(db, process.getUniqueID()))
                process.addTask(t);
            processes.add(process);
        }
        cursor.close();
        return processes;
    }

    public static Process[] getUsersProcesses(SQLiteDatabase db, int userID) {
        return null;
    }

    private static ArrayList<Task> getFollowedTasks(SQLiteDatabase db, int processID) {
        String[] projection = {
                ProcessTaskEntry.COLUMN_TASK,
                ProcessTaskEntry.COLUMN_ORDER
        };

        String whereClause= ProcessTaskEntry.COLUMN_PROCESS + " = ?";
        String[] whereArgs = {
                Integer.toString(processID)
        };

        String orderBy = ProcessTaskEntry.COLUMN_ORDER;
        //Query the database for all entries
        Cursor cursor = db.query(ProcessTaskEntry.TABLE_NAME, projection, whereClause, whereArgs, null, null, orderBy);

        ArrayList<Task> tasks = new ArrayList<>();

        while(cursor.moveToNext()) {
            tasks.add(getTask(db, cursor.getInt(cursor.getColumnIndexOrThrow(ProcessTaskEntry.COLUMN_TASK))));
        }
        cursor.close();
        return tasks;
    }

    public static void updateProcess(SQLiteDatabase db, Process process) {
        ContentValues values = new ContentValues();

        values.put(ProcessEntry.COLUMN_PROCESS_TITLE, process.getTitle());
        values.put(ProcessEntry.COLUMN_PROCESS_DESCRIPTION, process.getDescription());
        values.put(ProcessEntry.COLUMN_PROCESS_CATEGORY, process.getCategory().name());
        values.put(ProcessEntry.COLUMN_PROCESS_CREATOR_ID, process.getCreatorID());
        String whereClause = "_id = ?";

        String[] whereArgs = new String[] {
                Integer.toString(process.getUniqueID())
        };

        db.update(ProcessEntry.TABLE_NAME, values, whereClause, whereArgs);
    }
}
