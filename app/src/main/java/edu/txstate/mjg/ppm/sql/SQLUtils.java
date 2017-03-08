package edu.txstate.mjg.ppm.sql;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

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

        return null;
    }

    public static ArrayList<Process> getAllProcesses(SQLiteDatabase db) {
        //Decide which columns we want to get
        String[] projection = {
            ProcessEntry._ID,
            ProcessEntry.COLUMN_PROCESS_TITLE,
            ProcessEntry.COLUMN_PROCESS_DESCRIPTION,
            ProcessEntry.COLUMN_PROCESS_CATEGORY
        };

        //Query the database for all entries
        Cursor cursor = db.query(ProcessEntry.TABLE_NAME, projection, null, null, null, null, null);

        ArrayList<Process> processes = new ArrayList<>();
        while(cursor.moveToNext()) {
            processes.add(new Process(cursor.getString(cursor.getColumnIndexOrThrow(ProcessEntry.COLUMN_PROCESS_TITLE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(ProcessEntry.COLUMN_PROCESS_DESCRIPTION)),
                    cursor.getString(cursor.getColumnIndexOrThrow(ProcessEntry.COLUMN_PROCESS_CATEGORY)), 0));
        }
        cursor.close();
        return processes;
    }
    public static Process[] getUsersProcesses(SQLiteDatabase db, int userID) {
        return null;
    }

    public static Task getTask(SQLiteDatabase db, int taskID) {
        return null;
    }

    public static void updateTask(SQLiteDatabase db) {

    }

    //TODO: Implement parsing the tasks
    public static Task[] parseTaskList(SQLiteDatabase db, String csvTasks) {
        return null;
    }

}
