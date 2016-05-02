package com.example.i7.jobbalagom.localDatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Strandberg95 on 2016-05-01.
 * Updated by Anton Gusyafsson on 2016-05-02
 * Creates database and it's tables.
 */

public class UserDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "USERINFO.DB";
    private static final int DATABASE_VERSION = 2;

    private static final String CREATE_USER_QUERY =
            "CREATE TABLE " + UserContract.User.TABLE_NAME
                   + "( " + UserContract.User.USER_NAME + " varchar(100) PRIMARY KEY,"
                   + UserContract.User.USER_EARNED + " FLOAT, "
                   + UserContract.User.USER_INCOME + " FLOAT, "
                   + UserContract.User.USER_TAX + " FLOAT);";

    private static final String CREATE_JOB_QUERY =
            "CREATE TABLE " + UserContract.Job.TABLE_NAME
                   + "( " + UserContract.Job.JOB_NAME + " varchar(50) PRIMARY KEY, "
                   + UserContract.Job.JOB_USER + " varchar(100), "
                   + UserContract.Job.JOB_PAY + " FLOAT, "
                   + UserContract.Job.JOB_OB + " FLOAT);";

    private static final String CREATE_SHIFT_QUERY =
            "CREATE TABLE " + UserContract.Shift.TABLE_NAME
                    + "( " +UserContract.Shift.SHIFT_ID + "INTEGER PRIMARY KEY AUTO_INCREMENT, "
                    + UserContract.Shift.SHIFT_JOB_NAME + " varchar(100), "
                    + UserContract.Shift.SHIFT_START + " FLOAT, "
                    + UserContract.Shift.SHIFT_END + " FLOAT, "
                    + UserContract.Shift.SHIFT_DATE + " INTEGER, "
                    + UserContract.Shift.SHIFT_HOURS_WORKED + " FLOAT);";


    private static final String CREATE_EXPENSE_QUERY =
            "CREATE TABLE " + UserContract.Expense.TABLE_NAME
                    + "( " +UserContract.Expense.EXPENSE_ID + "INTEGER PRIMARY KEY AUTO_INCREMENT, "
                    + UserContract.Expense.EXPENSE_NAME + " varchar(100), "
                    + UserContract.Expense.EXPENSE_SUM + " FLOAT, "
                    + UserContract.Expense.EXPENSE_DATE + " INTEGER); ";

    /**
     * If the Version hasn't changed, it will just open the stored database.
     * If the version has changed, it will create a new database.
     * @param context
     */
    public UserDbHelper(Context context){
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
        Log.e("DBTAG", "Table Created...");
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
     * @param name
     * @param user
     * @param pay
     * @param ob
     * @param db
     */
    public void addJob(String name, String user, float pay, float ob, SQLiteDatabase db  ){
        ContentValues contentValues = new ContentValues();
        contentValues.put(UserContract.Job.JOB_NAME,name);
        contentValues.put(UserContract.Job.JOB_USER,user);
        contentValues.put(UserContract.Job.JOB_PAY, pay);
        contentValues.put(UserContract.Job.JOB_OB, ob);
        db.insert(UserContract.Job.TABLE_NAME, null, contentValues);
        Log.e("DBTAG", "Information added jobbTable");
    }

    /**
     * Adds a shift to shift table.
     * @param jobName
     * @param start
     * @param end
     * @param date
     * @param hoursWorked
     * @param db
     */
    public void addShift(String jobName, float start, float end, int date, float hoursWorked, SQLiteDatabase db ){
        ContentValues contentValues = new ContentValues();
        contentValues.put(UserContract.Shift.SHIFT_JOB_NAME,jobName);
        contentValues.put(UserContract.Shift.SHIFT_START,start);
        contentValues.put(UserContract.Shift.SHIFT_END, end);
        contentValues.put(UserContract.Shift.SHIFT_DATE, date);
        contentValues.put(UserContract.Shift.SHIFT_HOURS_WORKED, hoursWorked);
        db.insert(UserContract.Job.TABLE_NAME, null, contentValues);
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

    /**
     * Not sure what this does yet...
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
