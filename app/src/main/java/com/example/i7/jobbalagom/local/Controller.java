package com.example.i7.jobbalagom.local;

import android.util.Log;

import com.example.i7.jobbalagom.XLSManager.Calculator;
import com.example.i7.jobbalagom.XLSManager.XlsToDatabase;

/**
 * Created by Strandberg95 on 2016-03-21.
 */
public class Controller  {

    private final String IP = "10.2.12.211";//ÄNDRA IP VID TESTNING!!!!!!
    private final int PORT = 4545;
    private Calculator calc;
    private ServerConnection serverConnection;
    private MessageListener listener;



    public Controller(){
        listener = new MessageListener();
        calc = new Calculator();
        serverConnection = new ServerConnection(listener,IP,PORT);

        Log.d("filereader","Startar i controller" + "\t");
        XlsToDatabase reader = new XlsToDatabase();
    }

    public float getCurrentTax(){
        return calc.getTax();
    }

    private class MessageListener implements MessageCallback{

        @Override
        public void updateMessage(float tax) {
            calc.setTax(tax);
        }
    }
}