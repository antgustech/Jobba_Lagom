package com.example.i7.jobbalagom.localDatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Anton, Kajsa, Christoffer, Morgan and Jakup
 * Contains methods for setting and getting data in the internal database.
 */

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "INTERNAL.DB";
    private static final int DATABASE_VERSION = 1;

    private static final String CREATE_USER_QUERY =
            "CREATE TABLE " + UserContract.User.TABLE_NAME
                    + "( " + UserContract.User.USER_TAX + " FLOAT, "
                    + UserContract.User.USER_INCOME_LIMIT + " FLOAT, "
                    + UserContract.User.USER_EMAIL + " TEXT,"
                    + UserContract.User.USER_PASSWORD + " TEXT," +
                    UserContract.User.USER_MUNICIPALITY + " TEXT" + ");";

    private static final String CREATE_JOB_QUERY =
            "CREATE TABLE " + UserContract.Job.TABLE_NAME
                    + "( " + UserContract.Job.JOB_TITLE + " TEXT PRIMARY KEY, "
                    + UserContract.Job.JOB_WAGE + " FLOAT);";

    private static final String CREATE_SHIFT_QUERY =
            "CREATE TABLE " + UserContract.Shift.TABLE_NAME
                    + "( " +UserContract.Shift.SHIFT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + UserContract.Shift.SHIFT_JOB_TITLE + " TEXT, "
                    + UserContract.Shift.SHIFT_START_TIME + " FLOAT, "
                    + UserContract.Shift.SHIFT_END_TIME + " FLOAT, "
                    + UserContract.Shift.SHIFT_YEAR + " INTEGER, "
                    + UserContract.Shift.SHIFT_MONTH + " INTEGER, "
                    + UserContract.Shift.SHIFT_DAY + " INTEGER, "
                    + UserContract.Shift.SHIFT_INCOME + " FLOAT, "
                    + UserContract.Shift.SHIFT_HOURS_WORKED + " FLOAT);";

    private static final String CREATE_EXPENSE_QUERY =
            "CREATE TABLE " + UserContract.Expense.TABLE_NAME
                    + "( " +UserContract.Expense.EXPENSE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + UserContract.Expense.EXPENSE_NAME + " TEXT, "
                    + UserContract.Expense.EXPENSE_AMOUNT + " FLOAT, "
                    + UserContract.Expense.EXPENSE_YEAR + " INTEGER, "
                    + UserContract.Expense.EXPENSE_MONTH + " INTEGER, "
                    + UserContract.Expense.EXPENSE_DAY + " INTEGER);";

    private static final String CREATE_OB_QUERY =
            "CREATE TABLE " + UserContract.OB.TABLE_NAME
                    + "( " + UserContract.OB.OB_JOBTITLE + " TEXT, "
                    + UserContract.OB.OB_DAY + " TEXT, "
                    + UserContract.OB.OB_FROMTIME + " TEXT, "
                    + UserContract.OB.OB_TOTIME + " TEXT, "
                    + UserContract.OB.OB_INDEX + " FLOAT, "
                    + UserContract.OB.OB_ID + " INTEGER PRIMARY KEY AUTOINCREMENT); ";

    public DBHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.e("DBHelper", "Internal database open");
    }

    /**
     * Creates the tables
     * Will only be used if the table does not exist.
     */

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER_QUERY);
        db.execSQL(CREATE_JOB_QUERY);
        db.execSQL(CREATE_SHIFT_QUERY);
        db.execSQL(CREATE_EXPENSE_QUERY);
        db.execSQL(CREATE_OB_QUERY);
        Log.e("DBHelper", "Tables created");
    }

    /**
     * Drop tables if they exists
     */

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + UserContract.User.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + UserContract.Job.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + UserContract.Shift.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + UserContract.Expense.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + UserContract.OB.TABLE_NAME);
        Log.e("DBHelper", "Dropped all tables");
        onCreate(db);
    }

    /**
     * Add a user to db.
     * @param tax the taxRate aquried from the external database.
     * @param incomeLimit the incomeLimit choosen by the user.
     * @param db reference to class that contains db methods.
     * @param municipality the choosen municipality, also aquried from the eexternal db.
     */

    public void addUser(float tax, float incomeLimit, SQLiteDatabase db, String municipality ){
        ContentValues contentValues = new ContentValues();
        contentValues.put(UserContract.User.USER_TAX, tax);
        contentValues.put(UserContract.User.USER_INCOME_LIMIT, incomeLimit);
        contentValues.put(UserContract.User.USER_MUNICIPALITY, municipality);
        db.insert(UserContract.User.TABLE_NAME, null, contentValues);
        Log.e("Internal DB", "New user: " + tax + " in tax, " + incomeLimit + " in income limit");
    }

    /**
     * Add a job to db.
     * @param jobTitle the name of the job.
     * @param wage the wage choosen by the user.
     * @param db reference to class that contains db methods.
     */

    public void addJob(String jobTitle, float wage, SQLiteDatabase db) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(UserContract.Job.JOB_TITLE, jobTitle);
        contentValues.put(UserContract.Job.JOB_WAGE, wage);
        db.insert(UserContract.Job.TABLE_NAME, null, contentValues);
        Log.e("Internal DB", "New job: " + jobTitle + ", wage: " + wage);
    }

    /**
     * Add a shift to db.
     * @param jobTitle the name of the job.
     * @param startTime the starting time for the shift
     * @param endTime the end time for the shift
     * @param hoursWorked the startTime-endTime.
     * @param year the registered year
     * @param month the registered month
     * @param day the registered day
     * @param income the registered complete income for the shift.
     * @param db reference to class that contains db methods.
     */

    public void addShift(String jobTitle, float startTime, float endTime, float hoursWorked, int year, int month, int day, float income, SQLiteDatabase db ){
        ContentValues contentValues = new ContentValues();
        contentValues.put(UserContract.Shift.SHIFT_JOB_TITLE, jobTitle);
        contentValues.put(UserContract.Shift.SHIFT_START_TIME, startTime);
        contentValues.put(UserContract.Shift.SHIFT_END_TIME, endTime);
        contentValues.put(UserContract.Shift.SHIFT_HOURS_WORKED, hoursWorked);
        contentValues.put(UserContract.Shift.SHIFT_YEAR, year);
        contentValues.put(UserContract.Shift.SHIFT_MONTH, month);
        contentValues.put(UserContract.Shift.SHIFT_DAY, day);
        contentValues.put(UserContract.Shift.SHIFT_INCOME, income);
        db.insert(UserContract.Shift.TABLE_NAME, null, contentValues);
        Log.e("Internal DB", "New shift: ");
    }

    /**
     * Add expense to db.
     * @param name the name of the expense.
     * @param sum the sum of the expense.
     * @param year the registered year
     * @param month the registered month
     * @param day the registered day
     * @param db reference to class that contains db methods.
     */

    public void addExpense(String name, float sum, int year, int month, int day, SQLiteDatabase db ){
        ContentValues contentValues = new ContentValues();
        contentValues.put(UserContract.Expense.EXPENSE_NAME,name);
        contentValues.put(UserContract.Expense.EXPENSE_AMOUNT,sum);
        contentValues.put(UserContract.Expense.EXPENSE_YEAR, year);
        contentValues.put(UserContract.Expense.EXPENSE_MONTH, month);
        contentValues.put(UserContract.Expense.EXPENSE_DAY, day);
        db.insert(UserContract.Expense.TABLE_NAME, null, contentValues);
        Log.e("Internal DB", "New expense: " + year + "" + month + "" + day + ", " + name + ", " + sum + " kr");
    }

    /**
     * Add ob rate for the registered job.
     * @param jobTitle the name of the job.
     * @param day the registered day.
     * @param fromTime the time the ob rate begins.
     * @param toTime the time the ob rate ends
     * @param obIndex the index for the wage is 1 and everything over that is for ob. An Index of 1.5 means that we have 50% of the wage is ob.
     * @param db reference to class that contains db methods.
     */

    public void addOB(String jobTitle, String day, String fromTime, String toTime, Float obIndex, SQLiteDatabase db) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(UserContract.OB.OB_JOBTITLE, jobTitle);
        contentValues.put(UserContract.OB.OB_DAY, day);
        contentValues.put(UserContract.OB.OB_FROMTIME, fromTime);
        contentValues.put(UserContract.OB.OB_TOTIME, toTime);
        contentValues.put(UserContract.OB.OB_INDEX, obIndex);
        db.insert(UserContract.OB.TABLE_NAME, null, contentValues);
        Log.e("Internal DB", "New OB: " + jobTitle + ", " + (obIndex-1)*100 + " % extra på " + day);
    }

    /**
     * Sets the tax rate for the user.
     * @param currentTax the tax gotten from the external db.
     * @param db reference to class that contains db methods.
     */

    public void setTax(float currentTax, SQLiteDatabase db){
        db.execSQL("UPDATE " + UserContract.User.TABLE_NAME + " SET " + UserContract.User.USER_TAX + "='"+ currentTax + "';");
        Log.e("Internal DB", "Setting tax in database to : " + currentTax);
    }

    /**
     * Sets the income limit for the user.
     * @param limit the limit choosen by the user.
     * @param db reference to class that contains db methods.
     */

    public void setIncomeLimit(float limit, SQLiteDatabase db){
        db.execSQL("UPDATE " + UserContract.User.TABLE_NAME + " SET " + UserContract.User.USER_INCOME_LIMIT + "='"+ String.valueOf(limit) + "';");
        Log.e("Internal DB", "Setting income limit in db");
    }

    /**
     * Removes the choosen job from the db.
     * @param jobTitle the name of the job.
     * @param db reference to class that contains db methods.
     */

    public void deleteJob(String jobTitle, SQLiteDatabase db){
        db.execSQL("DELETE FROM " + UserContract.Job.TABLE_NAME + " WHERE " + UserContract.Job.JOB_TITLE + " = '"+ jobTitle +"';");
        Log.e("Internal DB", "Deleted job: " + jobTitle);
    }

    /**
     * Removes the user from the db.
     * @param db reference to class that contains db methods.
     */

    public void deleteUser(SQLiteDatabase db){
        db.execSQL("DELETE * FROM " + UserContract.User.TABLE_NAME + "');");
        Log.e("Internal DB", "Deleted user");
    }

    /**
     * Removes shift from the db.
     * @param id the id of the shift.
     * @param db reference to class that contains db methods.
     */

    public void deleteShift(int id, SQLiteDatabase db){
        db.execSQL("DELETE FROM " + UserContract.Shift.TABLE_NAME + " WHERE " + UserContract.Shift.SHIFT_ID+ " = '" + id + "';");
        Log.e("Internal DB", "Deleted shift: " + id);
    }

    /**
     * Removes expense from the db.
     * @param name the name of the expense.
     * @param db reference to class that contains db methods.
     */

    public void deleteExpense(String name, SQLiteDatabase db){
        db.execSQL("DELETE FROM " + UserContract.Expense.TABLE_NAME + " WHERE " + UserContract.Expense.EXPENSE_NAME + " = '" + name + "';");
        Log.e("Internal DB", "Deleted expense: " + name);
    }

    /**
     * Adds all the expenses amounts together used for setting the graphs in mainActivity.
     * @param db reference to class that contains db methods.
     * @return the sum of all expense.
     */


    public float getTotalExpense(SQLiteDatabase db){
        float totalExpense = 0;
        Cursor c = db.rawQuery("SELECT SUM( " + UserContract.Expense.EXPENSE_AMOUNT + " ) FROM " + UserContract.Expense.TABLE_NAME + ";", null);
        if(c.moveToFirst()) {
            totalExpense = c.getFloat(0);
        }
        Log.e("Internal DB", "Getting total expense totalExpense from database");
        return totalExpense;
    }

    /**
     * Return either first half or last half of year income-
     * @param month the registered month.
     * @param year the registered year.
     * @param db reference to class that contains db methods.
     * @return the income of either month 1-6 or 6-12.
     */

    public float getHalfYearIncome(int month, int year, SQLiteDatabase db) {
        float halfYearIncome = 0;
        Cursor c;
        if(month <= 6) {
            c = db.rawQuery("SELECT SUM(" + UserContract.Shift.SHIFT_INCOME + ") FROM " + UserContract.Shift.TABLE_NAME
                    + " WHERE " + UserContract.Shift.SHIFT_MONTH + " >= 1 AND " + UserContract.Shift.SHIFT_MONTH + " <= 6"
                    + " AND " + UserContract.Shift.SHIFT_YEAR + " = " + year + ";", null);
        } else {
            c = db.rawQuery("SELECT SUM(" + UserContract.Shift.SHIFT_INCOME + ") FROM " + UserContract.Shift.TABLE_NAME
                    + " WHERE " + UserContract.Shift.SHIFT_MONTH + " >= 7 AND " + UserContract.Shift.SHIFT_MONTH + " <= 12"
                    + " AND " + UserContract.Shift.SHIFT_YEAR + " = " + year + ";", null);
        }
        if(c.moveToFirst()) {
            halfYearIncome = c.getFloat(0);
        }
        Log.e("Internal DB", "Getting half year (" + year + "." + month + ") income from database: " + halfYearIncome);
        return halfYearIncome;
    }

    /**
     * Return the income for the choosen month.
     * @param month the choosen month.
     * @param year the current year.
     * @param db reference to class that contains db methods.
     * @return the income for the choosen month as a float.
     */

    public float getMonthlyIncome(int month, int year, SQLiteDatabase db) {
        float income = 0;

        Cursor c = db.rawQuery("SELECT SUM(" + UserContract.Shift.SHIFT_INCOME + ") FROM " + UserContract.Shift.TABLE_NAME + " WHERE "
                + UserContract.Shift.SHIFT_MONTH + " = " + month + " AND " + UserContract.Shift.SHIFT_YEAR + " = " + year + ";", null);
        if(c.moveToFirst()) {
            income = c.getFloat(0);
        }
        Log.e("Internal DB", "GETTING MONTHLY INCOME (" + year + "." + month + ") from database: " + income);
        return income;
    }

    /**
     * Returns the choosen month and year's sum of expenses.
     * @param month the choosen month.
     * @param year the choosen year.
     * @param db reference to class that contains db methods.
     * @return the sum of expense.
     */

    public float getMonthlyExpenses(int month, int year, SQLiteDatabase db) {
        float expenses = 0;
        Cursor c = db.rawQuery("SELECT SUM(" + UserContract.Expense.EXPENSE_AMOUNT + ") FROM " + UserContract.Expense.TABLE_NAME + " WHERE "
                + UserContract.Expense.EXPENSE_MONTH + " = " + month + " AND " + UserContract.Expense.EXPENSE_YEAR + " = " + year + ";", null);

        if(c.moveToFirst()) {
            expenses = c.getFloat(0);
        }
        Log.e("Internal DB", "GETTING MONTHLY EXPENSES (" + year + "." + month + ") from database: " + expenses);
        return expenses;
    }

    /**
     * Gets the current month income
     * @param db reference to class that contains db methods.
     * @return this month incsome as a float.
     */

    public float getThisMonthsIncome(SQLiteDatabase db) {
        float thisMonthsIncome = 0;
        int currentMonth = Calendar.getInstance().get(Calendar.MONTH) + 1;
        int currentYear = Calendar.getInstance().get(Calendar.YEAR)%100;

        Cursor c = db.rawQuery("SELECT SUM(" + UserContract.Shift.SHIFT_INCOME + ") FROM " + UserContract.Shift.TABLE_NAME
                                + " WHERE " + UserContract.Shift.SHIFT_MONTH + " = " + currentMonth
                                + " AND " + UserContract.Shift.SHIFT_YEAR + " = " + currentYear + ";", null);
        if(c.moveToFirst()) {
            thisMonthsIncome = c.getFloat(0);
            Log.e("DBBBB", c.toString());
        }
        Log.e("Internal DB", "Getting this months (" + currentYear + "." + currentMonth + ") income from database: " + thisMonthsIncome);
        return thisMonthsIncome;
    }

    /**
     * Gets this month expense.
     * @param db reference to class that contains db methods.
     * @return this months expense as a float.
     */

    public float getThisMonthsExpenses(SQLiteDatabase db) {
        float thisMonthsExpenses = 0;
        int currentMonth = Calendar.getInstance().get(Calendar.MONTH) + 1;
        int currentYear = Calendar.getInstance().get(Calendar.YEAR)%100;

        Cursor c = db.rawQuery("SELECT SUM(" + UserContract.Expense.EXPENSE_AMOUNT + ") FROM " + UserContract.Expense.TABLE_NAME
                + " WHERE " + UserContract.Expense.EXPENSE_MONTH + " = " + currentMonth
                + " AND " + UserContract.Expense.EXPENSE_YEAR + " = " + currentYear + ";", null);
        if(c.moveToFirst()) {
            thisMonthsExpenses = c.getFloat(0);
        }
        Log.e("Internal DB", "Getting this months (" + currentYear + "." + currentMonth + ") expenses from database: " + thisMonthsExpenses);
        return thisMonthsExpenses;
    }


    /**
     * Gets the incomeLimit set by the user
     * @param db reference to class that contains db methods.
     * @return the set incomeLimit as a float.
     */

    public float getIncomeLimit(SQLiteDatabase db) {
        float incomeLimit = 0;
        Cursor c = db.rawQuery("SELECT " + UserContract.User.USER_INCOME_LIMIT + " FROM " + UserContract.User.TABLE_NAME + ";", null);
        if(c.moveToFirst()) {
            incomeLimit = c.getFloat(0);
        }
        Log.e("Internal DB", "Getting income limit sum from database " + incomeLimit );
        return incomeLimit;
    }

    /**
     * Gets all the job names for setting the spinner so the user can se their registered jobs.
     * @param db reference to class that contains db methods.
     * @return string array with all the job names.
     */

    public String[] getJobTitles(SQLiteDatabase db){
        ArrayList<String> jobs = new ArrayList<String>();
        Cursor c = db.rawQuery("SELECT " + UserContract.Job.JOB_TITLE + " FROM " + UserContract.Job.TABLE_NAME, null);
        while(c.moveToNext()){
            String jobTitle = c.getString(c.getColumnIndex(UserContract.Job.JOB_TITLE));
            jobs.add(jobTitle);
        }
        String[] jobTitles = jobs.toArray(new String[jobs.size()]);
        Log.e("Internal DB", "Getting job titles from database");
        return jobTitles;
    }

    /**
     * Gets the wage for the choosen job.
     * @param jobTitle the name of the job
     * @param db reference to class that contains db methods.
     * @return the wage as a float for the choosen job.
     */

    public float getWage(String jobTitle, SQLiteDatabase db) {
        float wage = 0;
        Cursor c = db.rawQuery("SELECT " + UserContract.Job.JOB_WAGE + " FROM " + UserContract.Job.TABLE_NAME
                + " WHERE " + UserContract.Job.JOB_TITLE + " = \"" + jobTitle + "\"", null);
        if (c.moveToFirst()) {
            wage = c.getFloat(c.getColumnIndex(UserContract.Job.JOB_WAGE));
        }
        Log.e("Internal DB", "Getting wage from database");
        return wage;
    }


    //TODO:REMOVE these two methods?
    public float getStartTime(SQLiteDatabase db) {
        float sum = 0;
        Cursor c = db.rawQuery("SELECT " + UserContract.Shift.SHIFT_START_TIME + " FROM " + UserContract.Shift.TABLE_NAME + ";", null);
        if (c.moveToFirst()) {
            sum = c.getFloat(c.getColumnIndex(UserContract.Shift.SHIFT_START_TIME));
        }
        Log.e("Internal DB", "Getting shift start time from database");
        return sum;
    }

    public float getEndTime(SQLiteDatabase db) {
        float sum = 0;
        Cursor c = db.rawQuery("SELECT " + UserContract.Shift.SHIFT_END_TIME + " FROM " + UserContract.Shift.TABLE_NAME + ";", null);
        if (c.moveToFirst()) {
            sum = c.getFloat(c.getColumnIndex(UserContract.Shift.SHIFT_END_TIME));
        }
        Log.e("Internal DB", "Getting shift end time from database");
        return sum;
    }


    /**
     * Gets the stored tax in the db.
     * @param db reference to class that contains db methods.
     * @return the set tax as a float.
     */

    public float getUserTax(SQLiteDatabase db) {
        float sum = 0;
        Cursor c = db.rawQuery("SELECT " + UserContract.User.USER_TAX + " FROM " + UserContract.User.TABLE_NAME + ";", null);
        if (c.moveToFirst()) {
            sum = c.getFloat(c.getColumnIndex(UserContract.User.USER_TAX));
        }
        Log.e("Internal DB", "Getting user tax from database " + sum  );
        return sum;

    }

    /**
     * Gets the registered municipality
     * @param db reference to class that contains db methods.
     * @return the set municipality as a string.
     */

    public String getUserMunicipality(SQLiteDatabase db){
        String municipality = "";
        Cursor c = db.rawQuery("SELECT " + UserContract.User.USER_MUNICIPALITY + " FROM " + UserContract.User.TABLE_NAME + ";", null);
        if(c.moveToFirst()){
            municipality = c.getString(c.getColumnIndex(UserContract.User.USER_MUNICIPALITY));
        }

        return municipality;
    }

    /**
     *  Gets the ob rate for the choosen job name and day.
     * @param jobTitle the name of the job.
     * @param day the day the ob was set to.
     * @param db reference to class that contains db methods.
     * @return the ob rate as a float.
     */

    public float getOB(String jobTitle, String day, SQLiteDatabase db){
        float sum = 0;
        Cursor c = db.rawQuery("SELECT " + UserContract.OB.OB_INDEX + " FROM " + UserContract.OB.TABLE_NAME + " WHERE jobTitle='" + jobTitle +"' AND day='"+ day +"';", null);
        if (c.moveToFirst()) {
            sum = c.getFloat(c.getColumnIndex(UserContract.OB.OB_INDEX));
        }
        Log.e("Internal DB", "Getting OB from database jobb:" + jobTitle+" day: "+ day + " obindex: " + sum);
        return sum;
    }

    /**
     * Gets the start time of the ob rate for the choosen job and day.
     * @param jobTitle the name of the job.
     * @param day the day the ob was set to.
     * @param db reference to class that contains db methods.
     * @return the time the ob rate begins as a float.
     */

    public float getOBStart(String jobTitle, String day, SQLiteDatabase db){
        float sum = 0;
        Cursor c = db.rawQuery("SELECT " + UserContract.OB.OB_FROMTIME + " FROM " + UserContract.OB.TABLE_NAME + " WHERE jobTitle ='" + jobTitle +"' AND day='"+day+"';", null);
        if (c.moveToFirst()) {
            sum = c.getFloat(c.getColumnIndex(UserContract.OB.OB_FROMTIME));
        }
        Log.e("Internal DB", "Getting OB start time from database");
        return sum;
    }

    /**
     * Gets the end time of the ob rate for the choosen job and day.
     * @param jobTitle the name of the job.
     * @param day the day the ob was set to.
     * @param db reference to class that contains db methods.
     * @return the time the ob rate ends as a float.
     */

    public float getOBEnd(String jobTitle, String day, SQLiteDatabase db){
        float sum = 0;
        Cursor c = db.rawQuery("SELECT " + UserContract.OB.OB_TOTIME + " FROM " + UserContract.OB.TABLE_NAME + " WHERE jobTitle ='" + jobTitle+"' AND day='"+day+"';", null);
        if (c.moveToFirst()) {
            sum = c.getFloat(c.getColumnIndex(UserContract.OB.OB_TOTIME));
        }
        Log.e("Internal DB", "Getting OB end time from database");
        return sum;
    }

    /**
     * Checks whenever a user has been created or not.
     * @param db reference to class that contains db methods.
     * @return true if there already is a user created.
     */

    public boolean isUserCreated(SQLiteDatabase db) {
        Cursor c = db.rawQuery("SELECT * FROM " + UserContract.User.TABLE_NAME + ";", null);
        if(c.getCount() == 0) {
            return false;
        } return true;
    }
}