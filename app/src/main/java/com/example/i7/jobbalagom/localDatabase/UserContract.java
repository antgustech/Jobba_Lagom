package com.example.i7.jobbalagom.localDatabase;

/**
 * Created by Strandberg95 on 2016-05-01.
 * Each inner abstract class represent a table in the database.
 */
public class UserContract {

    public static abstract class User{
        public static final String TABLE_NAME = "user";
        public static final String USER_NAME = "name";
        public static final String USER_TAX = "tax";
        public static final String USER_EARNED = "earned";
        public static final String USER_INCOME = "income";
    }

    public static abstract class Job{
        public static final String TABLE_NAME = "job";
        public static final String JOB_USER="userName";
        public static final String JOB_NAME = "name";
        public static final String JOB_PAY = "pay";
        public static final String JOB_OB = "ob";
    }

    public static abstract class Shift{
        public static final String TABLE_NAME = "shift";
        public static final String SHIFT_ID = "shiftID";
        public static final String SHIFT_JOB_NAME = "jobID";
        public static final String SHIFT_START = "shiftstart";
        public static final String SHIFT_END = "shiftend";
        public static final String SHIFT_DATE = "date";
        public static final String SHIFT_HOURS_WORKED = "workedhours";
    }

    public static abstract class Expense{
        public static final String TABLE_NAME = "expense";
        public static final String EXPENSE_ID = "expenseID";
        public static final String EXPENSE_NAME = "name";
        public static final String EXPENSE_SUM = "sum";
        public static final String EXPENSE_DATE = "date";
    }
}
