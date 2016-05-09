package com.example.i7.jobbalagom.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.i7.jobbalagom.callback_interfaces.MessageCallback;
import com.example.i7.jobbalagom.localDatabase.DBHelper;
import com.example.i7.jobbalagom.remote.Client;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Strandberg95 on 2016-03-21.
 *
 */

public class Controller  {

    private final String IP = "192.168.1.136";//ÄNDRA IP VID TESTNING!!!!!!
    private final int PORT = 4545;

    private Calculator calc;
    private Client client;
    private MessageListener listener;
    private DBHelper dbHelper;
    private SQLiteDatabase sqLiteDatabase;
    private String user = "ettnamn";

    public Controller(Context context){
        Log.e("DBTAG", "Controller created!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        listener = new MessageListener();
        calc = new Calculator();
        client = new Client(listener,IP,PORT);

        //TESTING
        dbHelper = new DBHelper(context);
        Singleton.setDBHelper(dbHelper);
        // addUser("Chris", 30.3f, 10000, 130.17f);
        //  addJob("Rörmockare", 100, 3.5f);
        // addShift("Rörmockare", 0900f,1700,0302);
        // addExpense("Glass", 5000f, 050216);

        // deleteUser("Chris");
        // deleteJob("Rörmockare");
        // deleteShift(1);
        // deleteExpense("Glass");

        //getExpenseSum();
        //getUserEarned();
        //getUserIncome();

//        getJobTitles();



    }


    private class MessageListener implements MessageCallback {

        public void updateKommun(String kommun){
        }

        @Override
        public void updateCities(String cities) {

        }

        @Override
        public void updateTax(float tax) {

        }
    }
    /**
     *---------------------External Database methods
     */

    public ArrayList<String> getKommun(){
        ArrayList<String> kommuner = null;
        kommuner = client.getKommunFromClient();

        return kommuner;
    }

    public Float getChurchTax(String kommun){
        Float tax = 0f;
        tax = client.getChurchTax(kommun);
        return tax;
    }

    public Float getTax(String kommun){
        Float tax = 0f;
        tax = client.getTax(kommun);
        return tax;
    }

    /**
     * Saves the choosen tax into database
     * @param currentTax
     */
    public void setTax(Float currentTax){
        sqLiteDatabase = dbHelper.getWritableDatabase();
        dbHelper.setTax(currentTax, sqLiteDatabase );
    }

    /**
     *---------------------Internal Database methods
     */


    /**
     * Adds user to db
     */
    public void addUser(String name, float tax, float earned, float income){
        sqLiteDatabase = dbHelper.getWritableDatabase();
        dbHelper.addUser(name,earned,income,tax,sqLiteDatabase);
        Log.d("DBTAG", "Information added");
        dbHelper.close();
    }


    /**
     * Adds job to db
     */

    public void addJob(String title, float wage) {
        sqLiteDatabase = dbHelper.getWritableDatabase();
        dbHelper.addJob(user, title, wage, sqLiteDatabase);
        Log.d("DBTAG", "Information added");
        dbHelper.close();
    }

    /**
     * Adds shift to db
     */
    public void addShift(String jobTitle, float startTime, float endTime, float hoursWorked, int date){
        sqLiteDatabase = dbHelper.getWritableDatabase();
        dbHelper.addShift(jobTitle, startTime, endTime, hoursWorked, date, sqLiteDatabase);
        Log.d("DBTAG", "Information added");
        dbHelper.close();
    }

    /**
     * Adds expense to db
     */
    public void addExpense(String name, Float sum, int date){
        sqLiteDatabase = dbHelper.getWritableDatabase();
        dbHelper.addExpense(name, sum, date, sqLiteDatabase);
        Log.d("DBTAG", "Information added");
        dbHelper.close();
    }

    /**
     * Delete User from table where String=userName
     */
    public void deleteUser(String name ) {
        sqLiteDatabase = dbHelper.getWritableDatabase();
        dbHelper.deleteUser(name,sqLiteDatabase);
        dbHelper.close();
    }

    /**
     * Delete Job from table where String=jobName
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
  /*  public ArrayList<Float> getExpenseSum(){
        ArrayList<Float> list;
        sqLiteDatabase = dbHelper.getReadableDatabase();
        list = dbHelper.getExpenseSum(sqLiteDatabase);
        dbHelper.close();
        return list;

    }
    */

    /**
     * Returns sum of all expenses as a float.
     * Could possible be used when setting the expense bar in mainactivity.
     */
    public Float getExpenseSum(){
        Float sum = null;
        sqLiteDatabase = dbHelper.getReadableDatabase();
        sum = dbHelper.getExpenseSum(sqLiteDatabase);
        dbHelper.close();
        return sum;

    }

    /**
     * Returns sum of all income as a float.
     * Could be used for setting income bar in mainactivity.
     * @return
     */
    public Float getUserIncome(){
        Float sum = null;
        sqLiteDatabase = dbHelper.getReadableDatabase();
        sum = dbHelper.getUserIncome(sqLiteDatabase);
        dbHelper.close();
        return sum;

    }

    /**
     * Returns sum of everyhting the user has earned as a float.
     * Could be used when setting main bar.
     * @return
     */
    public Float getUserEarned(){
        Float sum = null;
        sqLiteDatabase = dbHelper.getReadableDatabase();
        sum = dbHelper.getUserEarned(sqLiteDatabase);
        dbHelper.close();
        return sum;

    }

    public String[] getJobTitles(){
        String[] jobTitles;
        sqLiteDatabase = dbHelper.getReadableDatabase();
        jobTitles = dbHelper.getJobTitles(sqLiteDatabase);
        dbHelper.close();
        return jobTitles;
    }

    public Float getJobPay() {
        Float sum = null;
        sqLiteDatabase = dbHelper.getReadableDatabase();
        sum = dbHelper.getJobPay(sqLiteDatabase);
        dbHelper.close();
        return sum;
    }

    public Float getStartTime() {
        Float sum = null;
        sqLiteDatabase = dbHelper.getReadableDatabase();
        sum = dbHelper.getStartTime(sqLiteDatabase);
        dbHelper.close();
        return sum;
    }

    public Float getEndTime() {
        Float sum = null;
        sqLiteDatabase = dbHelper.getReadableDatabase();
        sum = dbHelper.getEndTime(sqLiteDatabase);
        dbHelper.close();
        return sum;
    }

    public Float getTax() {
        Float sum = null;
        sqLiteDatabase = dbHelper.getReadableDatabase();
        sum = dbHelper.getUserTax(sqLiteDatabase);
        dbHelper.close();
        return sum;
    }


    public void addOB(String jobTitle, String day, String fromTime, String toTime, Float obIndex) {
        sqLiteDatabase = dbHelper.getWritableDatabase();
        dbHelper.addOB(jobTitle, day, fromTime, toTime, obIndex, sqLiteDatabase);
        Log.d("DBTAG", "Information added");
        dbHelper.close();
    }


    public Float getOB(){
        Float sum = null;
        sqLiteDatabase = dbHelper.getWritableDatabase();
        sum = dbHelper.getOB(sqLiteDatabase);
        Log.d("DBTAG", "Information added");
        dbHelper.close();
        return sum;

    }


    public Float getOBStart(){
        Float sum = null;
        sqLiteDatabase = dbHelper.getWritableDatabase();
        sum = dbHelper.getOBStart(sqLiteDatabase);
        Log.d("DBTAG", "Information added");
        dbHelper.close();
        return sum;
    }


    public Float getOBEnd(){

        Float sum = null;
        sqLiteDatabase = dbHelper.getWritableDatabase();
        sum =dbHelper.getOBEnd(sqLiteDatabase);
        Log.d("DBTAG", "Information added");
        dbHelper.close();
        return sum;

    }

}

