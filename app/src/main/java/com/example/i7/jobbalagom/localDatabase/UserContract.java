package com.example.i7.jobbalagom.localDatabase;

/**
 * Created by Strandberg95 on 2016-05-01.
 * Each inner abstract class represent a table in the database.
 */
public class UserContract {

    public static abstract class User {
        public static final String TABLE_NAME = "user";
        public static final String USER_NAME = "name";
        public static final String USER_TAX = "tax";
        public static final String USER_EARNED = "earned";//Behövas retuneras för huvud baren
        public static final String USER_INCOME = "income";//Behövs retuneras för top bar
    }

    public static abstract class Job {
        public static final String TABLE_NAME = "job";
        public static final String JOB_USER="user";
        public static final String JOB_TITLE = "title";
        public static final String JOB_WAGE = "wage";
    }

    public static abstract class Shift {
        public static final String TABLE_NAME = "shift";
        public static final String SHIFT_ID = "shiftID";
        public static final String SHIFT_JOB_NAME = "jobName";
        public static final String SHIFT_START = "shiftStart";
        public static final String SHIFT_END = "shiftEnd";
        public static final String SHIFT_DATE = "date";
        public static final String SHIFT_HOURS_WORKED = "workedHours";
    }

    public static abstract class Expense {
        public static final String TABLE_NAME = "expense";
        public static final String EXPENSE_ID = "expenseID";
        public static final String EXPENSE_NAME = "name";
        public static final String EXPENSE_SUM = "sum";  //behövs retuneras för top bar
        public static final String EXPENSE_DATE = "date";
    }

    public static abstract class OB {
        public static final String TABLE_NAME = "ob";
        public static final String OB_JOBTITLE = "jobTitle";
        public static final String OB_DAY = "day";
        public static final String OB_FROMTIME = "fromTime";
        public static final String OB_TOTIME = "toTime";
        public static final String OB_INDEX = "obIndex";
        public static final String OB_ID = "id";
    }
}
