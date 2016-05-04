package com.example.i7.jobbalagom.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.i7.jobbalagom.remote.Client;
import com.example.i7.jobbalagom.localDatabase.DBHelper;

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

    //private Context context = this;
    private DBHelper dbHelper;
    private SQLiteDatabase sqLiteDatabase;

    public Controller(Context context){
        listener = new MessageListener();
        calc = new Calculator();
        client = new Client(listener,IP,PORT);
        //dbHelper =DataHolder.getInstance().getDbHelper();
        dbHelper = new DBHelper(context);
        addUser("Chris", 30.3f, 10000, 130.17f);
        addJob("Rörmockare", "Chris", 100, 3.5f);
        addShift("Rörmockare", 0900f,1700,33,8);
        addExpense("Glass", 50f, 050216);
    }


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


    public ArrayList<String> getKommun() throws IOException, ClassNotFoundException {
        ArrayList<String> kommuner = null;
            kommuner = client.getKommunFromClient();

        return kommuner;
    }

    public void getCity(){

    }
    public void getTax(){

    }

}