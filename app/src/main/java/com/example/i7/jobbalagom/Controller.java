package com.example.i7.jobbalagom;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.io.IOException;
import java.net.Socket;

/**
 * Created by Strandberg95 on 2016-03-21.
 */
public class Controller  {

    private final String IP = "192.168.0.194";
    private final int PORT = 4545;

    private Calculator calc;
    private ServerConnection serverConnection;
    private MessageListener listener;


    public Controller(){
        listener = new MessageListener();
        calc = new Calculator();
        serverConnection = new ServerConnection(listener,IP,PORT);
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