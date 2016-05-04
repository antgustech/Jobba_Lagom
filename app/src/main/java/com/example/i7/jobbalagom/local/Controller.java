package com.example.i7.jobbalagom.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.i7.jobbalagom.localDatabase.DBHelper;
import com.example.i7.jobbalagom.remote.Client;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Strandberg95 on 2016-03-21.
 *
 */
public class Controller  {

    private final String IP = "192.168.1.19";//ÄNDRA IP VID TESTNING!!!!!!
    private final int PORT = 4545;

    private Calculator calc;
    private Client client;
    private MessageListener listener;
    private DBHelper dbHelper;
    private SQLiteDatabase sqLiteDatabase;

    public Controller(Context context){
        Log.e("DBTAG", "Controller created!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        listener = new MessageListener();
        calc = new Calculator();
        client = new Client(listener,IP,PORT);

        //TESTING
        dbHelper = new DBHelper(context);
        Singleton.setDBHelper(dbHelper);
        addUser("Chris", 30.3f, 10000, 130.17f);
        addJob("Rörmockare", "Chris", 100, 3.5f);
        addShift("Rörmockare", 0900f,1700,33,8);
        addExpense("Glass", 50f, 050216);

       // deleteUser("Chris");
       // deleteJob("Rörmockare");
       // deleteShift(1);
       // deleteExpense("Glass");

        getExpenseSum();



    }


    private class MessageListener implements MessageCallback{

        public void updateKommun(String kommun){
        }

        @Override
        public void updateCities(String cities) {

        }

        @Override
        public void updateTax(float tax) {
            calc.setTax();
        }
    }
    /**
     *---------------------External Database methods
     */

    public ArrayList<String> getKommun() throws IOException, ClassNotFoundException {
        ArrayList<String> kommuner = null;
            kommuner = client.getKommunFromClient();

        return kommuner;
    }

    public void getCity(){

    }
    public void getTax(){

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
    public void addJob(String name, String user, float pay, float ob){
        sqLiteDatabase = dbHelper.getWritableDatabase();
        dbHelper.addJob(name, user, pay, ob,sqLiteDatabase);
        Log.d("DBTAG", "Information added");
        dbHelper.close();
    }

    /**
     * Adds shift to db
     */
    public void addShift(String jobName, float start, float end, int date, float hoursWorked){
        sqLiteDatabase = dbHelper.getWritableDatabase();
        dbHelper.addShift(jobName, start, end, date, hoursWorked, sqLiteDatabase);
        Log.d("DBTAG", "Information added");
        dbHelper.close();
    }

    /**
     * Adds expense to db
     */
    public void addExpense(String name, float sum, int date){
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
     * Returns all sums from all rows from the expense table as a list.
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
     */
    public Float getExpenseSum(){
        Float sum = null;
        sqLiteDatabase = dbHelper.getReadableDatabase();
        sum = dbHelper.getExpenseSum(sqLiteDatabase);
        dbHelper.close();
        return sum;

    }

}