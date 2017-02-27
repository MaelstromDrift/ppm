package edu.txstate.mjg.ppm.sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import edu.txstate.mjg.ppm.sql.PPMDatabaseContract.ProcessEntry;

public class SQLiteDBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "ppm.db";

    private static int DATABASE_VERSION = 1;

    public SQLiteDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_TABLE = "CREATE TABLE "  + ProcessEntry.TABLE_NAME +
                "(" + ProcessEntry._ID + "INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ProcessEntry.COLUMN_PROCESS_TITLE + "TEXT NOT NULL" +
                ProcessEntry.COLUMN_PROCESS_DESCRIPTION + "TEXT NOT NULL" +
                ProcessEntry.COLUMN_PROCESS_CATEGORY + "INTEGER NOT NULL" +
                ProcessEntry.COLUMN_PROCESS_CREATOR_ID + "INTEGER NOT NULL" + ");";

        db.execSQL(SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ProcessEntry.TABLE_NAME + ";");
        onCreate(db);
    }
}

