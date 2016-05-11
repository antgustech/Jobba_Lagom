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
    private float clientTax = 0;

    // private DataOutputStream dos;
    private MessageCallback messageCallback;
    private String IP;
    private int PORT;

    public Client(MessageCallback messageCallback, String ip, int port){
        controller  = Singleton.controller;
        this.IP = ip;
        this.PORT = port;
        this.messageCallback = messageCallback;
        municipalities = new ArrayList<String>();
        if(!this.isAlive())
            start();
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
            socket = new Socket(IP, PORT);
            dis = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            dos = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            ois = new ObjectInputStream((socket.getInputStream()));
            municipalities = getMunicipalitiesFromServer();
        } catch (IOException e) {}
    }

    /**
     * Returns all the kommuns as a list.
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */

    public ArrayList<String> getMunicipalitiesFromServer() {
        ArrayList<String> list = new ArrayList<String>();
        try {
            dos.writeInt(1);
            dos.flush();
            list = (ArrayList) ois.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return list;
    }

    public ArrayList<String> getMunicipalities(){
        return municipalities;
    }

    /**
     * Retrieves tax including churchtax as a float.
     * @param municipality
     * @return
     */

    public float getChurchTax(String municipality){
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
     * Retrieves tax excluding churchtax as a float.
     * @param municipality
     * @return
     */

    public float getTaxFromServer(String municipality){
        final String kommunGetter = municipality;
        setTaxClient(0);

        Thread t = new Thread(new Runnable() {
            public void run(){
                try {
                    dos.writeInt(3);
                    dos.flush();
                    dos.writeUTF(kommunGetter);
                    dos.flush();
                    setTaxClient(dis.readFloat());
                   // Log.e("dbTag",dis.readFloat() + "");
                } catch (IOException e) {}
            }
        });

        t.setPriority(Thread.MAX_PRIORITY);
        t.start();
        while(clientTax == 0){ // TODO TA BORT DENNA SKITEN OCH SYNCHA PÅ NÅTT JÄVLA SÄTT

        }
        return clientTax;
    }

    public void setTaxClient(float tax){
        this.clientTax = tax;
    }
}