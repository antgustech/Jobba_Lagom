package com.example.i7.jobbalagom.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.i7.jobbalagom.callback_interfaces.MessageCallback;
import com.example.i7.jobbalagom.localDatabase.DBHelper;
import com.example.i7.jobbalagom.remote.Client;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Strandberg95 on 2016-03-21.
 *
 */

public class Controller  {
    /**
     * -------ÄNDRA IP VID TESTNING-----------!
     */
        private final String IP = "192.168.1.136"; // Kajsa 192.168.0.10
    /**
     * ----------------------------------------!
     */

    private final int PORT = 4545;
    private Client client;
    private MessageListener listener;
    private DBHelper dbHelper;
    private SQLiteDatabase sqLiteDatabase;

    private float tax, result,wage,obIndex,obStart,obEnd;


    public Controller(Context context){
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

    public ArrayList<String> getMunicipalities() throws NullPointerException{
        return client.getMunicipalities();
    }

    public float getChurchTax(String municipality) {
        return client.getChurchTaxFromServer(municipality);
    }

    public float getTax(String municipality){
        return client.getTaxFromServer(municipality);
    }

    public boolean checkConnection(){
        return client.checkConnection();
    }


    /**
     *-------------- INTERNAL DATABASE METHODS, COMMUNICATION GOEST THROUGH DBHELPER -----------------------
     */

    /**
     * Adds user to db
     */

    public void addUser(String municipality, float incomeLimit) {
        float tax = client.getTaxFromServer(municipality);
        sqLiteDatabase = dbHelper.getWritableDatabase();
        dbHelper.addUser(tax, incomeLimit, sqLiteDatabase, municipality);
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

    public void addShift(String jobTitle, float startTime, float endTime, float hoursWorked, int year, int month, int day , float income){
        sqLiteDatabase = dbHelper.getWritableDatabase();
        dbHelper.addShift(jobTitle, startTime, endTime, hoursWorked, year, month, day, income, sqLiteDatabase);
        dbHelper.close();
    }

    /**
     * Adds expense to db
     */

    public void addExpense(String name, float sum, int year, int month, int day) {
        sqLiteDatabase = dbHelper.getWritableDatabase();
        dbHelper.addExpense(name, sum, year, month, day, sqLiteDatabase);
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
        dbHelper.deleteJob(name, sqLiteDatabase);
        dbHelper.close();
    }

    /**
     * Delete Shift from table where id=shiftID
     */

    public void deleteShift(int id ) {
        sqLiteDatabase = dbHelper.getWritableDatabase();
        dbHelper.deleteShift(id, sqLiteDatabase);
        dbHelper.close();
    }

    /**
     * Delete Expense from table where String=expenseName
     */

    public void deleteExpense(String name ) {
        sqLiteDatabase = dbHelper.getWritableDatabase();
        dbHelper.deleteExpense(name, sqLiteDatabase);
        dbHelper.close();
    }


    public boolean isUserCreated() {
        sqLiteDatabase = dbHelper.getReadableDatabase();
        boolean userCreated = dbHelper.isUserCreated(sqLiteDatabase);
        dbHelper.close();
        return userCreated;
    }

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

    public float getHalfYearIncome() {
        sqLiteDatabase = dbHelper.getReadableDatabase();
        float halfYearIncome = dbHelper.getHalfYearIncome(sqLiteDatabase);
        dbHelper.close();
        return halfYearIncome;
    }

    public float getThisMonthsIncome() {
        sqLiteDatabase = dbHelper.getReadableDatabase();
        float thisMonthsIncome = dbHelper.getThisMonthsIncome(sqLiteDatabase);
        dbHelper.close();
        return thisMonthsIncome;
    }

    public float getThisMonthsExpenses() {
        sqLiteDatabase = dbHelper.getReadableDatabase();
        float thisMonthsExpenses = dbHelper.getThisMonthsExpenses(sqLiteDatabase);
        dbHelper.close();
        return thisMonthsExpenses;
    }

    public float getIncomeLimit() {
        sqLiteDatabase = dbHelper.getReadableDatabase();
        float incomeLimit = dbHelper.getIncomeLimit(sqLiteDatabase);
        dbHelper.close();
        return incomeLimit;
    }

    public void setIncomeLimit(float limit){
        sqLiteDatabase = dbHelper.getReadableDatabase();
        dbHelper.setIncomeLimit(limit, sqLiteDatabase);
        dbHelper.close();
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


    public String getMunicipality() {
        sqLiteDatabase = dbHelper.getReadableDatabase();
        String municipality = dbHelper.getUserMunicipality(sqLiteDatabase);
        dbHelper.close();
        return municipality;
    }

    public float getOB( String jobTitle, String day){

        sqLiteDatabase = dbHelper.getWritableDatabase();
        float sum = dbHelper.getOB(jobTitle, day, sqLiteDatabase);
        dbHelper.close();

        return sum;
    }

    public float getOBStart(String jobTitle, String obDay){
        sqLiteDatabase = dbHelper.getWritableDatabase();
        float sum = dbHelper.getOBStart(jobTitle, obDay, sqLiteDatabase);
        dbHelper.close();
        return sum;
    }

    public float getOBEnd(String jobTitle, String obDay){
        sqLiteDatabase = dbHelper.getWritableDatabase();
        float sum =dbHelper.getOBEnd(jobTitle, obDay, sqLiteDatabase);
        dbHelper.close();
        return sum;
    }

    public void setTax(float tax){
        sqLiteDatabase = dbHelper.getWritableDatabase();
        dbHelper.setTax(tax, sqLiteDatabase );
        dbHelper.close();
    }

    public void removeJob(String jobTitle){
        sqLiteDatabase = dbHelper.getWritableDatabase();
        dbHelper.deleteJob(jobTitle,sqLiteDatabase);
        dbHelper.close();
    }

    /**
     * When user have pressed addShift this method is called to calculate the shift and then call addShift method to add the result.
     * TODO *More tests to make sure it calculates correct
     */
    public float caculateShift(String jobTitle, float startTime, float endTime, int year, int month, int day, float breakMinutes){
        float workedPay, workedTime,realTax, workedObPay = 0;
        int  dayOfWeek;
        String dayName;

        //Calculate which day it is.
        Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR)/100*100+year;
        c.set(year,month-1,day);
        dayOfWeek = c.get(Calendar.DAY_OF_WEEK) ; //Sunday = 1, Saturday = 7
        if(dayOfWeek>1 && dayOfWeek <7){
            dayName ="Vardag";
        }else if(dayOfWeek == 7){
            dayName ="Lördag";
        }else {
            dayName ="Söndag";
        }

        //Get data from the database
        wage = getWage(jobTitle);
        tax = getTax();
        obIndex = getOB(jobTitle, dayName);
        obStart= getOBStart(jobTitle, dayName);
        obEnd = getOBEnd(jobTitle, dayName);
        result = 0;

        //If ob is 0, it will be set to 1 so later it will just multiply with 1 instead of 0.
        if(obIndex == 0){
            obIndex++;
        }

        //Check if ob hours are within working hours.
        if(obStart<endTime){
            if(endTime>obEnd){
                workedObPay=((obEnd - obStart) * ((obIndex-1)*wage)) ;
            }else if(endTime<=obEnd){
                workedObPay= ((endTime-obStart)* ((obIndex-1)*wage));
            }
        }

        //Break handling, removes the breakMinutes from the middle of the shift.
        workedTime = (((endTime-startTime)/2) - breakMinutes) + ((endTime-startTime)/2);

        //Final calculation
        workedPay = workedTime * wage;
        realTax= 1 -(tax/100);
        result = ((workedPay + workedObPay) * realTax);

        //Send data to database and return the result.
        Log.e("Calculation ", "Result after calculation: wage: " + wage + " StartTime: " + startTime + " EndTime " + endTime + " tax " + tax + " new tax " + realTax + " obIndex " + obIndex + " obStart " + obStart + " obEnd " + obEnd + " day " + dayOfWeek + " = " + dayName + " calculated ob " + workedObPay + " Result= " + result);
        addShift(jobTitle, startTime, endTime, workedTime, year, month, day , result);

        return result;
    }
}

