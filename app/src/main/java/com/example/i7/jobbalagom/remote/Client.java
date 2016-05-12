package com.example.i7.jobbalagom.remote;

import com.example.i7.jobbalagom.callback_interfaces.MessageCallback;
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
 * Created by Anton Gustafsson on 2016-04-19.
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
    private MessageCallback messageCallback;
    private String host;
    private int port;

    public Client(MessageCallback messageCallback, String host, int port){
        controller  = Singleton.controller;
        this.host = host;
        this.port = port;
        this.messageCallback = messageCallback;
        if(!isAlive()) {
            start();
        }
    }

    public void closeConnection(){
        try {
            connected = false;
            socket.close();
            clientThread = null;
        } catch(IOException e){}
    }

    public void run(){
        try {
            socket = new Socket(host, port);
            dis = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            dos = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            ois = new ObjectInputStream((socket.getInputStream()));
            municipalities = getMunicipalitiesFromServer();
        } catch (IOException e) {}
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
     * Returns the related tax including church tax as a float
     */

    public float getChurchTaxFromServer(String municipality){
        float tax = 0f;
        try {
            dos.writeInt(2);
            dos.flush();
            dos.writeUTF(municipality);
            tax = dis.readFloat();
        } catch (IOException e) {
        }
        return tax;
    }

    /**
     * Returns the related tax excluding church tax as a float
     */

    public float getTaxFromServer(final String municipality) {
        Thread thread = new Thread(new Runnable() {
            public void run() {



                try {
                    dos.writeInt(3);
                    dos.flush();
                    dos.writeUTF(municipality);
                    dos.flush();
                    tax = dis.readFloat();
                } catch (IOException e) {
                }
            }
        });
        thread.setPriority(Thread.MAX_PRIORITY);
        thread.start();

        while (tax == 0) { // TODO TA BORT DENNA SKITEN OCH SYNCHA PÅ NÅTT JÄVLA SÄTT
        }
        return tax;
    }
}