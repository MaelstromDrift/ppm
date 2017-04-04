package edu.txstate.mjg.ppm.sql;

import android.provider.BaseColumns;

public class PPMDatabaseContract {
    public static final class ProcessEntry implements BaseColumns {
        public static final String TABLE_NAME = "processes";
        public static final String COLUMN_PROCESS_TITLE = "title";
        public static final String COLUMN_PROCESS_DESCRIPTION = "description";
        public static final String COLUMN_PROCESS_CATEGORY = "category";
        public static final String COLUMN_PROCESS_CREATOR_ID = "creatorID";

    }
    public static final class TaskEntry implements BaseColumns {
        public static final String TABLE_NAME = "tasks";
        public static final String COLUMN_TASK_TITLE = "title";
        public static final String COLUMN_TASK_DESCRIPTION = "description";
        public static final String COLUMN_TASK_CREATOR_ID = "creatorID";

    }
    public static final class ProcessTaskEntry implements BaseColumns {
        public static final String TABLE_NAME = "followed_tasks";
        public static final String COLUMN_PROCESS = "process_id";
        public static final String COLUMN_TASK = "task_id";
        public static final String COLUMN_ORDER = "task_order";
    }
}
