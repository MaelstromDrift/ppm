package edu.txstate.mjg.ppm.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import edu.txstate.mjg.ppm.sql.PPMDatabaseContract.ProcessEntry;
import edu.txstate.mjg.ppm.sql.PPMDatabaseContract.ProcessTaskEntry;
import edu.txstate.mjg.ppm.sql.PPMDatabaseContract.TaskEntry;

public class SQLiteDBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "ppm.db";

    private static int DATABASE_VERSION = 1;

    public SQLiteDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_PROCESS_TABLE = " CREATE TABLE "  + ProcessEntry.TABLE_NAME +
                "(" + ProcessEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ProcessEntry.COLUMN_PROCESS_TITLE + " TEXT NOT NULL," +
                ProcessEntry.COLUMN_PROCESS_DESCRIPTION + " TEXT NOT NULL," +
                ProcessEntry.COLUMN_PROCESS_CATEGORY + " TEXT NOT NULL," +
                ProcessEntry.COLUMN_PROCESS_CREATOR_ID + " INTEGER NOT NULL" + ");";

        final String SQL_CREATE_TASKS_TABLE = " CREATE TABLE "  + TaskEntry.TABLE_NAME +
                "(" +  TaskEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TaskEntry.COLUMN_TASK_TITLE + " TEXT NOT NULL," +
                TaskEntry.COLUMN_TASK_DESCRIPTION + " TEXT NOT NULL," +
                TaskEntry.COLUMN_TASK_CREATOR_ID + " INTEGER NOT NULL" + ");";

        //TODO: Learn about foreign keys/primary keys
        final String SQL_CREATE_FOLLOWING_TABLE = " CREATE TABLE " + ProcessTaskEntry.TABLE_NAME +
                ProcessTaskEntry.COLUMN_PROCESS + " INTEGER NOT NULL, " +
                ProcessTaskEntry.COLUMN_TASK + " INTEGER NOT NULL, " +
                ProcessTaskEntry.COLUMN_ORDER + " INTEGER NOT NULL" + ");";


        db.execSQL(SQL_CREATE_PROCESS_TABLE);
        db.execSQL(SQL_CREATE_TASKS_TABLE);
        db.execSQL(SQL_CREATE_FOLLOWING_TABLE);

       insertDefaultTask(db);
    }

    /**
     * This might not be needed anymore due to the new approach of tracking processes tasks.
     * @param db
     */
    private void insertDefaultTask(SQLiteDatabase db) {
        ContentValues values = new ContentValues();

        values.put(TaskEntry.COLUMN_TASK_TITLE, "Default Task");
        values.put(TaskEntry.COLUMN_TASK_DESCRIPTION, "This is the default task!");
        values.put(TaskEntry.COLUMN_TASK_CREATOR_ID, 0);

        db.insert(TaskEntry.TABLE_NAME, null, values);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ProcessEntry.TABLE_NAME + ";");
        db.execSQL("DROP TABLE IF EXISTS " + TaskEntry.TABLE_NAME + ";");
        db.execSQL("DROP TABLE IF EXISTS " + ProcessTaskEntry.TABLE_NAME + ";");
        onCreate(db);
    }
}

