package com.example.i7.jobbalagom.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.i7.jobbalagom.remote.Client;
import com.example.i7.jobbalagom.localDatabase.UserDbHelper;

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
    private UserDbHelper userDbHelper;
    private SQLiteDatabase sqLiteDatabase;

    public Controller(Context context){
        listener = new MessageListener();
        calc = new Calculator();
        client = new Client(listener,IP,PORT);
        userDbHelper = new UserDbHelper(context);
        addInformation();
    }

    public void addInformation(){
        String name = "Christoffer";
        float tax = 30.3f;
        float earned = 10000;
        float income = 130.17f;

        sqLiteDatabase = userDbHelper.getWritableDatabase();
        //userDbHelper.addInformations(name,earned,income,tax,sqLiteDatabase);
        Log.d("DBTAG", "Information added");
       // userDbHelper.close();
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

    public void testing(){
        Log.d("taxTag","Aylamo");
    }




}