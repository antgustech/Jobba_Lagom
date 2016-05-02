package com.example.i7.jobbalagom.local;

import android.provider.BaseColumns;

/**
 * Created by Anton on 2016-05-02.
 * Creates and interacts with internal database that stores infomrationa boutt he user, jobs, workpass.
*/
public final class DatabaseClient {
    public DatabaseClient() {}





    public static abstract class FeedEntryJob implements BaseColumns {
        public static final String TABLE_NAME = "Job";
        public static final String COLUMN_NAME_ENTRY_ID = "entryid";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_PAY = "pay";
        public static final String COLUMN_NAME_OB = "OB";

    }

    public static abstract class FeedEntryExpense implements BaseColumns {
        public static final String TABLE_NAME = "Expense";
        public static final String COLUMN_NAME_ENTRY_ID = "entryid";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_SUM = "sum";
        public static final String COLUMN_NAME_DATE = "date";
    }

    public static abstract class FeedEntryShift implements BaseColumns {
        public static final String TABLE_NAME = "Shift";
        public static final String COLUMN_NAME_ENTRY_ID = "entryid";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_JOB = "job";
        public static final String COLUMN_NAME_STARTTIME = "startTime";
        public static final String COLUMN_NAME_ENDTIME = "endTime";
        public static final String COLUMN_NAME_DATE = "Date";
        public static final String COLUMN_NAME_INCOME = "Income";


    }

  /*  public void createDatabase(){
         String TEXT_TYPE = " TEXT";
         String COMMA_SEP = ",";
         String SQL_CREATE_ENTRIES =
                "CREATE TABLE " + FeedEntry.TABLE_NAME + " (" +
                        FeedEntry._ID + " INTEGER PRIMARY KEY," +
                        FeedEntry.COLUMN_NAME_ENTRY_ID + TEXT_TYPE + COMMA_SEP +
                        FeedEntry.COLUMN_NAME_TITLE + TEXT_TYPE + COMMA_SEP + " )";

       String SQL_DELETE_ENTRIES =
                "DROP TABLE IF EXISTS " + FeedEntry.TABLE_NAME;
    }

*/



}
