package com.example.i7.jobbalagom.localDatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.LinkedList;

/**
 * Created by Strandberg95 on 2016-05-01.
 * Updated by Anton Gusyafsson on 2016-05-02
 * Creates database and it's tables and methods for adding/updating rows and look at them.
 */
public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "INTENAL.DB";
    private static final int DATABASE_VERSION = 13;


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
                    + UserContract.Job.JOB_WAGE + " FLOAT); ";

    private static final String CREATE_SHIFT_QUERY =
            "CREATE TABLE " + UserContract.Shift.TABLE_NAME
                    + "( " + UserContract.Shift.SHIFT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + UserContract.Shift.SHIFT_JOB_NAME + " TEXT, "
                    + UserContract.Shift.SHIFT_START + " FLOAT, "
                    + UserContract.Shift.SHIFT_END + " FLOAT, "
                    + UserContract.Shift.SHIFT_DATE + " INTEGER, "
                    + UserContract.Shift.SHIFT_HOURS_WORKED + " FLOAT);";

    private static final String CREATE_EXPENSE_QUERY =
            "CREATE TABLE " + UserContract.Expense.TABLE_NAME
                    + "( " + UserContract.Expense.EXPENSE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
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
     *
     * @param context
     */
    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.e("DBTAG", "Database created / Opened...");
    }

    /**
     * Creates the tables
     * Will only be used if the table does not exist.
     *
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
     *
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
     *
     * @param name
     * @param earned
     * @param income
     * @param tax
     * @param db
     */
    public void addUser(String name, float earned, float income, float tax, SQLiteDatabase db) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(UserContract.User.USER_NAME, name);
        contentValues.put(UserContract.User.USER_EARNED, earned);
        contentValues.put(UserContract.User.USER_INCOME, income);
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
    public void addJob(String title, String user, float wage, SQLiteDatabase db) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(UserContract.Job.JOB_USER, user);
        contentValues.put(UserContract.Job.JOB_TITLE, title);
        contentValues.put(UserContract.Job.JOB_WAGE, wage);
        db.insert(UserContract.Job.TABLE_NAME, null, contentValues);
        Log.e("DBTAG", "Information added jobTable");
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

    /**
     * Adds a shift to shift table.
     *
     * @param jobTitle
     * @param start
     * @param end
     * @param date
     * @param hoursWorked
     * @param db
     */
    public void addShift(String jobTitle, float start, float end, int date, float hoursWorked, SQLiteDatabase db) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(UserContract.Shift.SHIFT_JOB_NAME, jobTitle);
        contentValues.put(UserContract.Shift.SHIFT_START, start);
        contentValues.put(UserContract.Shift.SHIFT_END, end);
        contentValues.put(UserContract.Shift.SHIFT_DATE, date);
        contentValues.put(UserContract.Shift.SHIFT_HOURS_WORKED, hoursWorked);
        db.insert(UserContract.Shift.TABLE_NAME, null, contentValues);
        Log.e("DBTAG", "Information added shiftTable");
    }

    /**
     * Adds an expense to expense table.
     *
     * @param name
     * @param sum
     * @param date
     * @param db
     */
    public void addExpense(String name, float sum, int date, SQLiteDatabase db) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(UserContract.Expense.EXPENSE_NAME, name);
        contentValues.put(UserContract.Expense.EXPENSE_SUM, sum);
        contentValues.put(UserContract.Expense.EXPENSE_DATE, date);
        db.insert(UserContract.Expense.TABLE_NAME, null, contentValues);
        Log.e("DBTAG", "Information added ExpenseTable");
    }


    public void deleteUser(String name, SQLiteDatabase db) {
        db.execSQL("DELETE FROM user WHERE name='" + name + "';");
        Log.e("DBTAG", "Row deleted: user");
    }

    public void deleteJob(String name, SQLiteDatabase db) {
        db.execSQL("DELETE FROM job WHERE name='" + name + "';");
        Log.e("DBTAG", "Row deleted: job");
    }

    public void deleteShift(int id, SQLiteDatabase db) {
        db.execSQL("DELETE FROM shift WHERE shiftID='" + id + "';");
        Log.e("DBTAG", "Row deleted: Shift");
    }

    public void deleteExpense(String name, SQLiteDatabase db) {
        db.execSQL("DELETE FROM expense WHERE name='" + name + "';");
        Log.e("DBTAG", "Row deleted: Expense");
    }


    /*public ArrayList<Float> getExpenseSum(SQLiteDatabase db){
        ArrayList<Float> list = new ArrayList<Float>();
        Cursor crs = db.rawQuery("select sum from " + UserContract.Expense.TABLE_NAME , null);
        while(crs.moveToNext()){
            float sum = crs.getFloat(crs.getColumnIndex("sum"));
            list.add(sum);
        }

        Log.e("DBTAG", list.toString());
        Log.e("DBTAG", "getExpenseSum");

        return list;
    }
    */

    public Float getExpenseSum(SQLiteDatabase db) {
        Float sum = null;
        Cursor crs = db.rawQuery("select SUM(sum) from " + UserContract.Expense.TABLE_NAME, null);
        if (crs.moveToFirst()) {
            sum = crs.getFloat(0);
        }
        Log.e("DBTAG", sum.toString());
        Log.e("DBTAG", "getExpenseSum in dbHelper float");
        return sum;
    }

    public Float getUserIncome(SQLiteDatabase db) {
        Float sum = null;
        Cursor crs = db.rawQuery("select SUM(income) from " + UserContract.User.TABLE_NAME, null);
        if (crs.moveToFirst()) {
            sum = crs.getFloat(0);
        }
        Log.e("DBTAG", sum.toString());
        Log.e("DBTAG", "getIncome in dbHelper float");
        return sum;
    }


    public Float getUserEarned(SQLiteDatabase db) {
        Float sum = null;
        Cursor crs = db.rawQuery("select SUM(earned) from " + UserContract.User.TABLE_NAME, null);
        if (crs.moveToFirst()) {
            sum = crs.getFloat(0);
        }
        Log.e("DBTAG", sum.toString());
        Log.e("DBTAG", "getEarned in dbHelper float");
        return sum;
    }

    public String[] getJobTitles(SQLiteDatabase db) {
        LinkedList<String> jobs = new LinkedList<String>();
        Cursor c = db.rawQuery("SELECT " + UserContract.Job.JOB_TITLE + " FROM " + UserContract.Job.TABLE_NAME, null);
        while (c.moveToNext()) {
            jobs.add(c.getString(0));
        }
        String[] jobTitles = jobs.toArray(new String[jobs.size()]);
        return jobTitles;
    }





}