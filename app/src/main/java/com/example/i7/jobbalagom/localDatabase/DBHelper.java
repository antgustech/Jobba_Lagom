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
 * Updated by Anton Gusyafsson on 2016-05-02
 * Creates database and it's tables and methods for adding/updating rows and look at them.
 */
public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "INTENAL.DB";
    private static final int DATABASE_VERSION = 28;


    private static final String CREATE_USER_QUERY =
            "CREATE TABLE " + UserContract.User.TABLE_NAME
                    + "( " + UserContract.User.USER_NAME + " TEXT PRIMARY KEY,"
                    + UserContract.User.USER_EARNED + " FLOAT, "
                    + UserContract.User.USER_INCOME + " FLOAT, "
                    + UserContract.User.USER_TAX + " FLOAT);";

    private static final String CREATE_JOB_QUERY =
            "CREATE TABLE " + UserContract.Job.TABLE_NAME
                    + "( " + UserContract.Job.JOB_USER + " TEXT, "
                    + UserContract.Job.JOB_TITLE + " TEXT PRIMARY KEY, "
                    + UserContract.Job.JOB_WAGE + " FLOAT);";

    private static final String CREATE_SHIFT_QUERY =
            "CREATE TABLE " + UserContract.Shift.TABLE_NAME
                    + "( " +UserContract.Shift.SHIFT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + UserContract.Shift.SHIFT_JOB_TITLE + " TEXT, "
                    + UserContract.Shift.SHIFT_START_TIME + " FLOAT, "
                    + UserContract.Shift.SHIFT_END_TIME + " FLOAT, "
                    + UserContract.Shift.SHIFT_DATE + " INTEGER, "
                    + UserContract.Shift.SHIFT_HOURS_WORKED + " FLOAT);";

    private static final String CREATE_EXPENSE_QUERY =
            "CREATE TABLE " + UserContract.Expense.TABLE_NAME
                    + "( " +UserContract.Expense.EXPENSE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + UserContract.Expense.EXPENSE_NAME + " TEXT, "
                    + UserContract.Expense.EXPENSE_SUM + " FLOAT, "
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
     * @param context
     */
    public DBHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.e("DBTAG", "Database created / Opened...");
    }

    /**
     * Creates the tables
     * Will only be used if the table does not exist.
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER_QUERY);
        db.execSQL(CREATE_JOB_QUERY);
        db.execSQL(CREATE_SHIFT_QUERY);
        db.execSQL(CREATE_EXPENSE_QUERY);
        db.execSQL(CREATE_OB_QUERY);
        Log.e("DBTAG", "Table Created...");
    }

    /**
     * Drop tables if they exists
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + UserContract.User.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + UserContract.Job.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + UserContract.Shift.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + UserContract.Expense.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + UserContract.OB.TABLE_NAME);
        // create new tables
        onCreate(db);
    }

    /**
     * Adds a user to the user table.
     * @param name
     * @param earned
     * @param income
     * @param tax
     * @param db
     */
    public void addUser(String name, float earned, float income, float tax, SQLiteDatabase db ){
        ContentValues contentValues = new ContentValues();
        contentValues.put(UserContract.User.USER_NAME,name);
        contentValues.put(UserContract.User.USER_EARNED,earned);
        contentValues.put(UserContract.User.USER_INCOME,income);
        contentValues.put(UserContract.User.USER_TAX, tax);
        db.insert(UserContract.User.TABLE_NAME, null, contentValues);
        Log.e("DBTAG", "Information added userTable");
    }

    /**
     * Adds a job to job table.
     *
     * @param title
     * @param user
     * @param wage
     * @param db
     */

    public void addJob(String user, String title, float wage, SQLiteDatabase db) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(UserContract.Job.JOB_USER, user);
        contentValues.put(UserContract.Job.JOB_TITLE, title);
        contentValues.put(UserContract.Job.JOB_WAGE, wage);
        db.insert(UserContract.Job.TABLE_NAME, null, contentValues);
        Log.e("DBTAG", "Information added jobTable");
    }

    /**
     * Adds a shift to shift table.
     */
    public void addShift(String jobTitle, float startTime, float endTime, float hoursWorked, int date, SQLiteDatabase db ){
        ContentValues contentValues = new ContentValues();
        contentValues.put(UserContract.Shift.SHIFT_JOB_TITLE, jobTitle);
        contentValues.put(UserContract.Shift.SHIFT_START_TIME, startTime);
        contentValues.put(UserContract.Shift.SHIFT_END_TIME, endTime);
        contentValues.put(UserContract.Shift.SHIFT_HOURS_WORKED, hoursWorked);
        contentValues.put(UserContract.Shift.SHIFT_DATE, date);
        db.insert(UserContract.Shift.TABLE_NAME, null, contentValues);
        Log.e("DBTAG", "Information added shiftTable");
    }

    /**
     * Adds an expense to expense table.
     * @param name
     * @param sum
     * @param date
     * @param db
     */
    public void addExpense(String name, float sum, int date, SQLiteDatabase db ){
        ContentValues contentValues = new ContentValues();
        contentValues.put(UserContract.Expense.EXPENSE_NAME,name);
        contentValues.put(UserContract.Expense.EXPENSE_SUM,sum);
        contentValues.put(UserContract.Expense.EXPENSE_DATE, date);
        db.insert(UserContract.Expense.TABLE_NAME, null, contentValues);
        Log.e("DBTAG", "Information added ExpenseTable");
    }

    public void addOB(String jobTitle, String day, String fromTime, String toTime, Float obIndex, SQLiteDatabase db) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(UserContract.OB.OB_JOBTITLE, jobTitle);
        contentValues.put(UserContract.OB.OB_DAY, day);
        contentValues.put(UserContract.OB.OB_FROMTIME, fromTime);
        contentValues.put(UserContract.OB.OB_TOTIME, toTime);
        contentValues.put(UserContract.OB.OB_INDEX, obIndex);
        db.insert(UserContract.OB.TABLE_NAME, null, contentValues);
        Log.e("DBTAG", "Information added obTable");
    }


    public void deleteUser(String name, SQLiteDatabase db){
        db.execSQL("DELETE FROM user WHERE name='"+name+"';");
        Log.e("DBTAG", "Row deleted: user");
    }
    public void deleteJob(String name, SQLiteDatabase db){
        db.execSQL("DELETE FROM job WHERE name='"+name+"';");
        Log.e("DBTAG", "Row deleted: job");
    }
    public void deleteShift(int id, SQLiteDatabase db){
        db.execSQL("DELETE FROM shift WHERE shiftID='"+id+"';");
        Log.e("DBTAG", "Row deleted: Shift");
    }
    public void deleteExpense(String name, SQLiteDatabase db){
        db.execSQL("DELETE FROM expense WHERE name='"+name+"';");
        Log.e("DBTAG", "Row deleted: Expense");
    }

    public Float getExpenseSum(SQLiteDatabase db){
        Float sum = null;
        Cursor crs = db.rawQuery("select SUM(sum) from " + UserContract.Expense.TABLE_NAME , null);
        if(crs.moveToFirst())
        {
            sum = crs.getFloat(0);
        }
        Log.e("DBTAG", sum.toString());
        Log.e("DBTAG", "getExpenseSum in dbHelper float");
        return sum;
    }

    public Float getUserIncome(SQLiteDatabase db){
        Float sum = null;
        Cursor crs = db.rawQuery("select SUM(income) from " + UserContract.User.TABLE_NAME , null);
        if(crs.moveToFirst())
        {
            sum = crs.getFloat(0);
        }
        Log.e("DBTAG", sum.toString());
        Log.e("DBTAG", "getIncome in dbHelper float");
        return sum;
    }


    public Float getUserEarned(SQLiteDatabase db){
        Float sum = null;
        Cursor crs = db.rawQuery("select SUM(earned) from " + UserContract.User.TABLE_NAME , null);
        if(crs.moveToFirst())
        {
            sum = crs.getFloat(0);
        }
        Log.e("DBTAG", sum.toString());
        Log.e("DBTAG", "getEarned in dbHelper float");
        return sum;
    }

    /**
     * This will set all rows to the specified tax. But as we are only going to have 1 user at the time, this is not a problem.
     * @param currentTax
     * @param db
     */
    public void setTax(Float currentTax, SQLiteDatabase db){
        db.execSQL("UPDATE " + UserContract.User.TABLE_NAME + " SET " + UserContract.User.USER_TAX + "='"+ currentTax + "';");
        Log.e("DBTAG", "Row deleted: Expense");
    }

    public String[] getJobTitles(SQLiteDatabase db){
        ArrayList<String> jobs = new ArrayList<String>();
        Cursor crs = db.rawQuery("SELECT " + UserContract.Job.JOB_TITLE + " FROM " + UserContract.Job.TABLE_NAME, null);
        while(crs.moveToNext()){
            String jobTitle = crs.getString(crs.getColumnIndex(UserContract.Job.JOB_TITLE));
            jobs.add(jobTitle);
        }
        Log.e("DBTAG", jobs.toString());

        String[] jobTitles = jobs.toArray(new String[jobs.size()]);
        return jobTitles;
    }

    public Float getJobPay(SQLiteDatabase db) {
        Float sum = null;
        Cursor crs = db.rawQuery("select pay from " + UserContract.Job.JOB_WAGE, null);
        if (crs.moveToFirst()) {
            sum = crs.getFloat(0);
        }
        return sum;
    }

    public Float getStartTime(SQLiteDatabase db) {
        Float sum = null;
        Cursor crs = db.rawQuery("select shiftStart from " + UserContract.Shift.TABLE_NAME, null);
        if (crs.moveToFirst()) {
            sum = crs.getFloat(0);
        }
        return sum;

    }

    public Float getEndTime(SQLiteDatabase db) {
        Float sum = null;
        Cursor crs = db.rawQuery("select shiftEnd from " + UserContract.Shift.TABLE_NAME, null);
        if (crs.moveToFirst()) {
            sum = crs.getFloat(0);
        }
        return sum;

    }

    public Float getUserTax(SQLiteDatabase db) {
        Float sum = null;
        Cursor crs = db.rawQuery("select tax from " + UserContract.User.USER_TAX, null);
        if (crs.moveToFirst()) {
            sum = crs.getFloat(0);
        }
        return sum;

    }
    /*public ArrayList<Float> getExpenseSum(SQLiteDatabase db){
        ArrayList<Float> list = new ArrayList<Float>();
        Cursor crs = db.rawQuery("select sum from " + UserContract.Expense.TABLE_NAME , null);
        while(crs.moveToNext()){
            float sum = crs.getFloat(crs.getColumnIndex("sum"));
            list.add(sum);
        }
        Log.e("DBTAG", list.toString());
        return list;
    }
    */








}