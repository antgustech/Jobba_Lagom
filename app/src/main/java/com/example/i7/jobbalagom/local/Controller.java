package com.example.i7.jobbalagom.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.i7.jobbalagom.callback_interfaces.MessageCallback;
import com.example.i7.jobbalagom.localDatabase.DBHelper;
import com.example.i7.jobbalagom.remote.Client;

import java.util.ArrayList;

/**
 * Created by Strandberg95 on 2016-03-21.
 *
 */

public class Controller  {

    private final String IP = "192.168.0.10";          //ÄNDRA IP VID TESTNING!!!!!! // Kajsa 192.168.0.10
    private final int PORT = 4545;

    private Calculator calculator;
    private Client client;
    private MessageListener listener;
    private DBHelper dbHelper;
    private SQLiteDatabase sqLiteDatabase;

    public Controller(Context context){
        calculator = new Calculator();
        client = new Client(listener,IP,PORT);
        listener = new MessageListener();
        dbHelper = new DBHelper(context);
        Singleton.setDBHelper(dbHelper);
    }




    private class MessageListener implements MessageCallback {

        public void updateMunicipality(String municipality){
        }

        @Override
        public void updateCities(String cities) {

        }

        @Override
        public void updateTax(float tax) {

        }
    }

    /**
     *-------------- EXTERNAL DATABASE METHODS, COMMUNICATION GOES THROUGH CLIENT -----------------------
     */

    public ArrayList<String> getMunicipalities(){
        return client.getMunicipalities();
    }

    public float getChurchTax(String municipality){
        return client.getChurchTaxFromServer(municipality);
    }

    public float getTax(String municipality){
        return client.getTaxFromServer(municipality);
    }




    /**
     *-------------- INTERNAL DATABASE METHODS, COMMUNICATION GOEST THROUGH DBHELPER -----------------------
     */

    /**
     * Adds user to db
     */

    public void addUser(String municipality, float incomeLimit){
        float tax = client.getTaxFromServer(municipality);
        sqLiteDatabase = dbHelper.getWritableDatabase();
        dbHelper.addUser(tax, incomeLimit, sqLiteDatabase);
        dbHelper.close();
    }

    /**
     * Adds job to db
     */

    public void addJob(String title, float wage) {
        sqLiteDatabase = dbHelper.getWritableDatabase();
        dbHelper.addJob(title, wage, sqLiteDatabase);
        dbHelper.close();
    }

    /**
     * Adds OB to db
     */

    public void addOB(String jobTitle, String day, String fromTime, String toTime, Float obIndex) {
        sqLiteDatabase = dbHelper.getWritableDatabase();
        dbHelper.addOB(jobTitle, day, fromTime, toTime, obIndex, sqLiteDatabase);
        dbHelper.close();
    }

    /**
     * Adds shift to db
     */

    public float addShift(String jobTitle, float startTime, float endTime, float hoursWorked, int year, int month, int day){
        float wage = getWage(jobTitle);
        float income = wage*hoursWorked;
       // float income = calculator.calculateIncome();

        sqLiteDatabase = dbHelper.getWritableDatabase();
        dbHelper.addShift(jobTitle, startTime, endTime, hoursWorked, year, month, day, income, sqLiteDatabase);
        dbHelper.close();
        return income;
    }

    /**
     * Adds expense to db
     */

    public void addExpense(String name, Float sum, int date){
        sqLiteDatabase = dbHelper.getWritableDatabase();
        dbHelper.addExpense(name, sum, date, sqLiteDatabase);
        dbHelper.close();
    }

    /**
     * Delete User from table where String = userName
     */

    public void deleteUser(String name ) {
        sqLiteDatabase = dbHelper.getWritableDatabase();
        dbHelper.deleteUser(sqLiteDatabase);
        dbHelper.close();
    }

    /**
     * Delete Job from table where String=jobTitle
     */

    public void deleteJob(String name ) {
        sqLiteDatabase = dbHelper.getWritableDatabase();
        dbHelper.deleteJob(name,sqLiteDatabase);
        dbHelper.close();
    }

    /**
     * Delete Shift from table where id=shiftID
     */

    public void deleteShift(int id ) {
        sqLiteDatabase = dbHelper.getWritableDatabase();
        dbHelper.deleteShift(id,sqLiteDatabase);
        dbHelper.close();
    }

    /**
     * Delete Expense from table where String=expenseName
     */

    public void deleteExpense(String name ) {
        sqLiteDatabase = dbHelper.getWritableDatabase();
        dbHelper.deleteExpense(name,sqLiteDatabase);
        dbHelper.close();
    }

    /**
     * Returns stuff as a list.
     */
  /*  public ArrayList<Float> getTotalExpense(){
        ArrayList<Float> list;
        sqLiteDatabase = dbHelper.getReadableDatabase();
        list = dbHelper.getTotalExpense(sqLiteDatabase);
        dbHelper.close();
        return list;

    }
    */

    /**
     * Returns sum of all expenses as a float.
     * Could possible be used when setting the expense bar in mainactivity.
     */

    public float getTotalExpense(){
        sqLiteDatabase = dbHelper.getReadableDatabase();
        float totalExpense = dbHelper.getTotalExpense(sqLiteDatabase);
        dbHelper.close();
        return totalExpense;
    }

    /**
     * Returns sum of all income as a float.
     * Could be used for setting income bar in mainactivity.
     * @return
     */

    public float getTotalIncome() {
        sqLiteDatabase = dbHelper.getReadableDatabase();
        float totalIncome = dbHelper.getTotalIncome(sqLiteDatabase);
        dbHelper.close();
        return totalIncome;
    }

    public float getThisMonthIncome() {
        sqLiteDatabase = dbHelper.getReadableDatabase();
        float thisMonthIncome = dbHelper.getThisMonthIncome(sqLiteDatabase);
        dbHelper.close();
        return thisMonthIncome;
    }

    public float getIncomeLimit() {
        sqLiteDatabase = dbHelper.getReadableDatabase();
        float incomeLimit = dbHelper.getIncomeLimit(sqLiteDatabase);
        dbHelper.close();
        return incomeLimit;
    }

    public String[] getJobTitles(){
        sqLiteDatabase = dbHelper.getReadableDatabase();
        String[] jobTitles = dbHelper.getJobTitles(sqLiteDatabase);
        dbHelper.close();
        return jobTitles;
    }

    public float getWage(String jobTitle) {
        sqLiteDatabase = dbHelper.getReadableDatabase();
        float sum = dbHelper.getWage(jobTitle, sqLiteDatabase);
        dbHelper.close();
        return sum;
    }

    public float getStartTime() {
        sqLiteDatabase = dbHelper.getReadableDatabase();
        float sum = dbHelper.getStartTime(sqLiteDatabase);
        dbHelper.close();
        return sum;
    }

    public float getEndTime() {
        sqLiteDatabase = dbHelper.getReadableDatabase();
        float sum = dbHelper.getEndTime(sqLiteDatabase);
        dbHelper.close();
        return sum;
    }

    public float getTax() {
        sqLiteDatabase = dbHelper.getReadableDatabase();
        float sum = dbHelper.getUserTax(sqLiteDatabase);
        dbHelper.close();
        return sum;
    }

    public float getOB(){
        sqLiteDatabase = dbHelper.getWritableDatabase();
        float sum = dbHelper.getOB(sqLiteDatabase);
        dbHelper.close();
        return sum;
    }

    public float getOBStart(){
        sqLiteDatabase = dbHelper.getWritableDatabase();
        float sum = dbHelper.getOBStart(sqLiteDatabase);
        dbHelper.close();
        return sum;
    }

    public float getOBEnd(){
        sqLiteDatabase = dbHelper.getWritableDatabase();
        float sum =dbHelper.getOBEnd(sqLiteDatabase);
        dbHelper.close();
        return sum;
    }

    public void setTax(float tax){
        sqLiteDatabase = dbHelper.getWritableDatabase();
        dbHelper.setTax(tax, sqLiteDatabase );
    }
}

