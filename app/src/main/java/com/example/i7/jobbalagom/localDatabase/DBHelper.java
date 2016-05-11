package com.example.i7.jobbalagom.localDatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Strandberg95 on 2016-05-01.
 * Updated by Anton Gustafsson on 2016-05-02
 * Creates database and it's tables and methods for adding/updating rows and look at them.
 */

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "INTENAL.DB";
    private static final int DATABASE_VERSION = 3;


    private static final String CREATE_USER_QUERY =
            "CREATE TABLE " + UserContract.User.TABLE_NAME
                    + "( " + UserContract.User.USER_NAME + " TEXT PRIMARY KEY, "
                    + UserContract.User.USER_TAX + " FLOAT, "
                    + UserContract.User.USER_INCOME_LIMIT + " FLOAT);";

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
                    + UserContract.Shift.SHIFT_DATE + " INTEGER, "
                    + UserContract.Shift.SHIFT_INCOME + " FLOAT, "
                    + UserContract.Shift.SHIFT_HOURS_WORKED + " FLOAT);";

    private static final String CREATE_EXPENSE_QUERY =
            "CREATE TABLE " + UserContract.Expense.TABLE_NAME
                    + "( " +UserContract.Expense.EXPENSE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + UserContract.Expense.EXPENSE_NAME + " TEXT, "
                    + UserContract.Expense.EXPENSE_AMOUNT + " FLOAT, "
                    + UserContract.Expense.EXPENSE_DATE + " INTEGER); ";

    private static final String CREATE_OB_QUERY =
            "CREATE TABLE " + UserContract.OB.TABLE_NAME
                    + "( " + UserContract.OB.OB_JOBTITLE + " TEXT, "
                    + UserContract.OB.OB_DAY + " TEXT, "
                    + UserContract.OB.OB_FROMTIME + " TEXT, "
                    + UserContract.OB.OB_TOTIME + " TEXT, "
                    + UserContract.OB.OB_INDEX + " FLOAT, "
                    + UserContract.OB.OB_ID + " INTEGER PRIMARY KEY AUTOINCREMENT); ";

    /**
     * If the Version hasn't changed, it will just open the stored database.
     * If the version has changed, it will create a new database.
     */

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
        Log.d("DBHelper", "Tables created");
    }

    /**
     * Drop tables if they exists
     */

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + UserContract.User.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + UserContract.Job.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + UserContract.Shift.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + UserContract.Expense.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + UserContract.OB.TABLE_NAME);
        Log.d("DBHelper", "Dropped all tables");
        onCreate(db);
    }

    /**
     * Adds a user to the user table.
     */

    public void addUser(String name, float tax, float incomeLimit, SQLiteDatabase db ){
        ContentValues contentValues = new ContentValues();
        contentValues.put(UserContract.User.USER_NAME, name);
        contentValues.put(UserContract.User.USER_TAX, tax);
        contentValues.put(UserContract.User.USER_INCOME_LIMIT, incomeLimit);
        db.insert(UserContract.User.TABLE_NAME, null, contentValues);
        Log.e("Internal DB", "New user: " + name + ", " + tax + " in tax, " + incomeLimit + " in income limit");
    }

    /**
     * Adds a job to job table.
     */

    public void addJob(String title, float wage, SQLiteDatabase db) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(UserContract.Job.JOB_TITLE, title);
        contentValues.put(UserContract.Job.JOB_WAGE, wage);
        db.insert(UserContract.Job.TABLE_NAME, null, contentValues);
        Log.e("Internal DB", "New job: " + title + ", timlön: " + wage);
    }

    /**
     * Adds a shift to shift table.
     */

    public void addShift(String jobTitle, float startTime, float endTime, float hoursWorked, int date, float income, SQLiteDatabase db ){
        ContentValues contentValues = new ContentValues();
        contentValues.put(UserContract.Shift.SHIFT_JOB_TITLE, jobTitle);
        contentValues.put(UserContract.Shift.SHIFT_START_TIME, startTime);
        contentValues.put(UserContract.Shift.SHIFT_END_TIME, endTime);
        contentValues.put(UserContract.Shift.SHIFT_HOURS_WORKED, hoursWorked);
        contentValues.put(UserContract.Shift.SHIFT_DATE, date);
        contentValues.put(UserContract.Shift.SHIFT_INCOME, income);
        db.insert(UserContract.Shift.TABLE_NAME, null, contentValues);
        Log.e("Internal DB", "New shift: " + hoursWorked + " hours work on job " + jobTitle + ", date " + date + ", inkomst " + income + " kr");
    }

    /**
     * Adds an expense to expense table.
     */

    public void addExpense(String name, float sum, int date, SQLiteDatabase db ){
        ContentValues contentValues = new ContentValues();
        contentValues.put(UserContract.Expense.EXPENSE_NAME,name);
        contentValues.put(UserContract.Expense.EXPENSE_AMOUNT,sum);
        contentValues.put(UserContract.Expense.EXPENSE_DATE, date);
        db.insert(UserContract.Expense.TABLE_NAME, null, contentValues);
        Log.e("Internal DB", "New expense: " + date + ", " + name + ", " + sum + " kr");
    }

    public void addOB(String jobTitle, String day, String fromTime, String toTime, Float obIndex, SQLiteDatabase db) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(UserContract.OB.OB_JOBTITLE, jobTitle);
        contentValues.put(UserContract.OB.OB_DAY, day);
        contentValues.put(UserContract.OB.OB_FROMTIME, fromTime);
        contentValues.put(UserContract.OB.OB_TOTIME, toTime);
        contentValues.put(UserContract.OB.OB_INDEX, obIndex);
        db.insert(UserContract.OB.TABLE_NAME, null, contentValues);
        Log.e("Internal DB", "New OB: " + jobTitle + ", " + (obIndex-1)*100 + " % extra på " + day + "ar");
    }

    public void deleteUser(String name, SQLiteDatabase db){
        db.execSQL("DELETE FROM " + UserContract.User.TABLE_NAME + " WHERE " + UserContract.User.USER_NAME + " = '" + name + "');");
        Log.e("Internal DB", "Deleted user: " + name);
    }
    public void deleteJob(String jobTitle, SQLiteDatabase db){
        db.execSQL("DELETE FROM " + UserContract.Job.TABLE_NAME + " WHERE " + UserContract.Job.JOB_TITLE + " = '"+ jobTitle +"';");
        Log.e("Internal DB", "Deleted job: " + jobTitle);
    }
    public void deleteShift(int id, SQLiteDatabase db){
        db.execSQL("DELETE FROM " + UserContract.Shift.TABLE_NAME + " WHERE " + UserContract.Shift.SHIFT_ID+ " = '" + id + "';");
        Log.e("Internal DB", "Deleted shift: " + id);
    }
    public void deleteExpense(String name, SQLiteDatabase db){
        db.execSQL("DELETE FROM " + UserContract.Expense.TABLE_NAME + " WHERE " + UserContract.Expense.EXPENSE_NAME + " = '" + name + "';");
        Log.e("Internal DB", "Deleted expense: " + name);
    }

    public float getTotalExpense(SQLiteDatabase db){
        float sum = 0;
        Cursor c = db.rawQuery("SELECT SUM( " + UserContract.Expense.EXPENSE_AMOUNT + " ) FROM " + UserContract.Expense.TABLE_NAME + ";", null);
        if(c.moveToFirst()) {
            sum = c.getFloat(0);
        }
        Log.e("Internal DB", "Getting total expense sum from database");
        return sum;
    }

    public float getTotalIncome(SQLiteDatabase db) {
        float sum = 0;
        Cursor c = db.rawQuery("SELECT SUM(" + UserContract.Shift.SHIFT_INCOME + ") FROM " + UserContract.Shift.TABLE_NAME + ";", null);
        if(c.moveToFirst()) {
            sum = c.getFloat(0);
        }
        Log.e("Internal DB", "Getting total income sum from database");
        return sum;
    }

    public float getIncomeLimit(SQLiteDatabase db) {
        float incomeLimit = 0;
        Cursor c = db.rawQuery("SELECT " + UserContract.User.USER_INCOME_LIMIT + " FROM " + UserContract.User.TABLE_NAME + ";", null);
        if(c.moveToFirst()) {
            incomeLimit = c.getFloat(c.getColumnIndex(UserContract.User.USER_INCOME_LIMIT));
        }
        Log.e("Internal DB", "Getting income limit sum from database");
        return incomeLimit;
    }

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

    public float getUserTax(SQLiteDatabase db) {
        float sum = 0;
        Cursor c = db.rawQuery("SELECT " + UserContract.User.USER_TAX + " FROM " + UserContract.User.TABLE_NAME + ";", null);
        if (c.moveToFirst()) {
            sum = c.getFloat(c.getColumnIndex(UserContract.User.USER_TAX));
        }
        Log.e("Internal DB", "Getting user tax from database");
        return sum;

    }

    public float getOB(SQLiteDatabase db){
        float sum = 0;
        Cursor c = db.rawQuery("SELET " + UserContract.OB.OB_INDEX + " FROM " + UserContract.OB.TABLE_NAME + ";", null);
        if (c.moveToFirst()) {
            sum = c.getFloat(c.getColumnIndex(UserContract.OB.OB_INDEX));
        }
        Log.e("Internal DB", "Getting OB from database");
        return sum;
    }

    public float getOBStart(SQLiteDatabase db){
        float sum = 0;
        Cursor c = db.rawQuery("SELECT " + UserContract.OB.OB_FROMTIME + " FROM " + UserContract.OB.TABLE_NAME + ";", null);
        if (c.moveToFirst()) {
            sum = c.getFloat(c.getColumnIndex(UserContract.OB.OB_FROMTIME));
        }
        Log.e("Internal DB", "Getting OB start time from database");
        return sum;
    }

    public float getOBEnd(SQLiteDatabase db){
        float sum = 0;
        Cursor c = db.rawQuery("SELECT " + UserContract.OB.OB_TOTIME + " FROM " + UserContract.OB.TABLE_NAME + ";", null);
        if (c.moveToFirst()) {
            sum = c.getFloat(c.getColumnIndex(UserContract.OB.OB_TOTIME));
        }
        Log.e("Internal DB", "Getting OB end time from database");
        return sum;
    }

    /**
     * This will set all rows to the specified tax. But as we are only going to have 1 user at the time, this is not a problem.
     * @param currentTax
     * @param db
     */

    public void setTax(float currentTax, SQLiteDatabase db){
        db.execSQL("UPDATE " + UserContract.User.TABLE_NAME + " SET " + UserContract.User.USER_TAX + "='"+ currentTax + "';");
        Log.e("Internal DB", "Setting tax in database");
    }




    /*public ArrayList<Float> getTotalExpense(SQLiteDatabase db){
        ArrayList<Float> list = new ArrayList<Float>();
        Cursor c = db.rawQuery("select sum from " + UserContract.Expense.TABLE_NAME , null);
        while(c.moveToNext()){
            float sum = c.getFloat(c.getColumnIndex("sum"));
            list.add(sum);
        }
        Log.e("DBTAG", list.toString());
        return list;
    }
    */








}