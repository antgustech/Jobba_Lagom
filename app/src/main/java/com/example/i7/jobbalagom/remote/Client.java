package com.example.i7.jobbalagom.remote;

import android.util.Log;

import com.example.i7.jobbalagom.callbacks.UpdateTaxCallback;
import com.example.i7.jobbalagom.local.Controller;
import com.example.i7.jobbalagom.local.Singleton;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.sql.Connection;
import java.util.ArrayList;

/**
 * Created by Anton, Christoffer, Jakup, Morgan and Christoffer.
 * Client handles connection to the servern.
 */

public class Client extends Thread {

    private Socket socket;
    private DataInputStream dis;
    private DataOutputStream dos;
    private ObjectInputStream ois;
    private Connection dbConnection;
    private Thread clientThread;
    private Controller controller;
    private boolean connected = false;
    private ArrayList<String> municipalities;
    private float tax;
    private Thread thread;
    private String IP;
    private int PORT;

    /**
     * Initializes ip, port and controller also starts a thread.
     * @param IP to connect to.
     * @param PORT port to connect to.
     */

    public Client( String IP, int PORT){
        controller  = Singleton.controller;
        this.IP = IP;
        this.PORT = PORT;
        //startThread();
        start();
    }

    /**
     * Starts the thread.
     */

    private void startThread(){
        if (thread == null){
            thread = new Thread();
        }
    }

    /**
     * Check whenever we are connected the the server or not.
     * @return true if we have a connection.
     */

    public boolean checkConnection(){
        Pinger pinger = new Pinger();
        pinger.start();
        while(pinger.isAlive()){}
        return connected;
    }

    /**
     * Closes the connection to the server.
     */

    public void closeConnection(){
        try {
            connected = false;
            socket.close();
            clientThread = null;
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Thread that
     */

    public void run(){
        try {
            socket = new Socket(IP, PORT);
            dis = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            dos = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            ois = new ObjectInputStream((socket.getInputStream()));
            municipalities = getMunicipalitiesFromServer();
            connected = true;
        } catch (IOException e) {
            connected = false;
        }
    }

    public ArrayList<String> getMunicipalities() throws NullPointerException {
        return municipalities;
    }

    /**
     * Returns all municipalities as a list
     */

    public ArrayList<String> getMunicipalitiesFromServer() throws NullPointerException {
        Thread thread = new Thread(new Runnable() {
            public void run() {
                try {
                    dos.writeInt(1);
                    dos.flush();
                    municipalities = (ArrayList) ois.readObject();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.setPriority(Thread.MAX_PRIORITY);
        thread.start();
        return municipalities;
    }


    /**
     * Gets the churchTax from the server
     * @param municipality the choosen municipality from the user.
     * @return the average churchtax for the choosen municipality.
     */

    public float getChurchTaxFromServer(final String municipality){
        tax = 0;
        Thread thread = new Thread(new Runnable() {
            public void run() {
                try {
                    dos.writeInt(2);
                    dos.flush();
                    dos.writeUTF(municipality);
                    dos.flush();
                    tax = dis.readFloat();
                    Log.e("ThreadTag",tax + "");

                } catch (IOException e) {}
            }
        });
        thread.setPriority(Thread.MAX_PRIORITY);
        thread.start();
        while(tax == 0){
            Log.e("ThreadTag","tax not changed");
        }
        return tax;
    }



    public void getChurchTaxFromServer(final String municipality, final UpdateTaxCallback callback){
        tax = 0;
        Thread thread = new Thread(new Runnable() {
            public void run() {
                try {
                    dos.writeInt(2);
                    dos.flush();
                    dos.writeUTF(municipality);
                    dos.flush();
                    tax = dis.readFloat();
                    callback.UpdateTax(tax);
                    Log.e("ThreadTag",tax + "");

                } catch (IOException e) {}
            }
        });
        thread.setPriority(Thread.MAX_PRIORITY);
        thread.start();
    }

    /**
     * Returns the related tax excluding church tax as a float
     */

    public float getTaxFromServer(final String municipality) {
        tax = 0;
        Thread thread = new Thread(new Runnable() {
            public void run() {
                try {
                    dos.writeInt(3);
                    dos.flush();
                    dos.writeUTF(municipality);
                    dos.flush();
                    tax = dis.readFloat();
                    Log.e("ThreadTag",tax + "");

                } catch (IOException e) {}
            }
            });
        thread.setPriority(Thread.MAX_PRIORITY);
        thread.start();
        while(tax == 0){
            Log.e("ThreadTag","tax not changed");
        }
        return tax;

    }

    public void getTaxFromServer(final String municipality, final UpdateTaxCallback callback) {
        tax = 0;
        Thread thread = new Thread(new Runnable() {
            public void run() {
                try {
                    dos.writeInt(3);
                    dos.flush();
                    dos.writeUTF(municipality);
                    dos.flush();
                    tax = dis.readFloat();
                    callback.UpdateTax(tax);
                    Log.e("ThreadTag",tax + "");

                } catch (IOException e) {}
            }
        });
        thread.setPriority(Thread.MAX_PRIORITY);
        thread.start();
    }

    private class Pinger extends Thread {

        private Socket newSocket;
        private DataInputStream newDis;
        private DataOutputStream newDos;
        private ObjectInputStream newOis;

        public void run(){
            try {
                Log.e("PingTag","Trying to connect");
                newSocket = new Socket(IP, PORT);
                newDis = new DataInputStream(new BufferedInputStream(newSocket.getInputStream()));
                newDos = new DataOutputStream(new BufferedOutputStream(newSocket.getOutputStream()));
                newOis = new ObjectInputStream((newSocket.getInputStream()));
                newDos.writeInt(0);
                newDos.flush();

                Log.e("PingTag","Server Pinged");

                int answer = newDis.readInt();

                Log.e("PingTag",answer + "");

                if(municipalities == null){
                    municipalities = getMunicipalitiesFromServer();
                }
                dis = newDis;
                dos = newDos;
                ois = newOis;
                if(connected == false){
                    connected = true;
                    Log.e("PingTag", "new Connected!");
                }
            }catch(IOException e){
                connected = false;
                Log.e("PingTag","Server still down");
            }
        }
    }

    private class TaxGetter extends Thread {

        private float tax = 0;
        private DataOutputStream dos;
        private DataInputStream dis;
        private String kommun;

        public TaxGetter(DataOutputStream dos, DataInputStream dis, String kommun){
            this.dos = dos;
            this.dis = dis;
            this.kommun = kommun;
        }

        public void run(){
            try {
                dos.writeInt(3);
                dos.flush();
                dos.writeUTF(kommun);
                dos.flush();
                tax = dis.readFloat();
            } catch (IOException e) {
            }
        }

        public float getTax(){
            start();
            while(tax == 0){
                if(this.isInterrupted()){
                    start();
                }
            }
            return tax;
        }
    }
}

