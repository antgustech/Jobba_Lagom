package com.example.i7.jobbalagom.localDatabase;

/**
 * Created by Christoffer, Anton, Kajsa, Jakup and Morgan.
 * Holds String variables for the interal database colums.
 */
public class UserContract {

    public static abstract class User {
        public static final String TABLE_NAME = "user";
        public static final String USER_TAX = "tax";
        public static final String USER_INCOME_LIMIT = "incomeLimit";
        public static final String USER_EMAIL = "email";
        public static final String USER_PASSWORD = "password";
        public static final String USER_MUNICIPALITY = "municipality";
    }

    public static abstract class Job {
        public static final String TABLE_NAME = "job";
        public static final String JOB_TITLE = "title";
        public static final String JOB_WAGE = "wage";
    }

    public static abstract class Shift {
        public static final String TABLE_NAME = "shift";
        public static final String SHIFT_ID = "shiftID";
        public static final String SHIFT_JOB_TITLE = "jobTitle";
        public static final String SHIFT_START_TIME = "startTime";
        public static final String SHIFT_END_TIME = "endTime";
        public static final String SHIFT_HOURS_WORKED = "hoursWorked";
        public static final String SHIFT_YEAR = "year";
        public static final String SHIFT_MONTH = "month";
        public static final String SHIFT_DAY = "day";
        public static final String SHIFT_TAX_INCOME = "taxIncome";
        public static final String SHIFT_NO_TAX_INCOME = "noTaxIncome";
    }

    public static abstract class Expense {
        public static final String TABLE_NAME = "expense";
        public static final String EXPENSE_ID = "expenseID";
        public static final String EXPENSE_NAME = "name";
        public static final String EXPENSE_AMOUNT = "amount";
        public static final String EXPENSE_YEAR = "year";
        public static final String EXPENSE_MONTH = "month";
        public static final String EXPENSE_DAY = "day";
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
