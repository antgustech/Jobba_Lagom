package com.example.i7.jobbalagom.local;

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

    public Controller(){
        listener = new MessageListener();
        calc = new Calculator();
        client = new Client(listener,IP,PORT);
    }



    private class MessageListener implements MessageCallback{

        public void updateKommun(String kommun){
        }

        @Override
        public void updateCities(String cities) {

        }

        @Override
        public void updateTax(float tax) {
            calc.setTax(tax);
        }
    }


    public ArrayList<String> getKommun() {
        ArrayList<String> kommuner = null;
        try {
            kommuner = client.getKommun();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return kommuner;
    }
    public void getCity(){

    }
    public void getTax(){

    }



}