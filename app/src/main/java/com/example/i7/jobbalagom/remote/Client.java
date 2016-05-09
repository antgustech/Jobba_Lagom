package com.example.i7.jobbalagom.remote;

import com.example.i7.jobbalagom.local.Controller;
import com.example.i7.jobbalagom.callback_interfaces.MessageCallback;
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
    private Thread clientThread;
    private boolean connected = false;
    private Connection dbConnection;
    private Controller controller;
    private ArrayList<String> kommuner;

    // private DataOutputStream dos;
    private MessageCallback messageCallback;
    private String IP;
    private int PORT;

    public Client(MessageCallback messageCallback, String ip, int port){
        controller  = Singleton.controller;
        this.IP = ip;
        this.PORT = port;
        this.messageCallback = messageCallback;
        kommuner = new ArrayList<String>();
        if(!this.isAlive())
            start();
    }

    public void closeConnection(){
        try {
            connected = false;
            socket.close();
            clientThread = null;
        }catch(IOException e){
        }
    }

    public void run(){
        try {
            socket = new Socket(IP, PORT);
            dis = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            dos = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            ois = new ObjectInputStream((socket.getInputStream()));
            kommuner = getKommunFromServer();
        } catch (IOException e) {}
        catch(ClassNotFoundException e2){}
    }

    /**
     * Returns all the kommuns as a list.
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public ArrayList<String> getKommunFromServer() throws IOException, ClassNotFoundException {
        ArrayList<String> list = new ArrayList<String>();
        try {
            dos.writeInt(1);
            dos.flush();
            list = (ArrayList) ois.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    public ArrayList<String> getKommunFromClient(){
        return kommuner;
    }

    /**
     * Retrieves tax including churchtax as a float.
     * @param kommun
     * @return
     */
    public Float getChurchTax(String kommun){
        float tax = 0f;
        try {
            dos.writeInt(2);
            dos.flush();
            dos.writeUTF(kommun);
            tax = dis.readFloat();
        } catch (IOException e) {
        }
        return tax;
    }

    /**
     * Retrieves tax excluding churchtax as a float.
     * @param kommun
     * @return
     */
    public Float getTax(String kommun){
        float tax = 0f;
        try {
            dos.writeInt(3);
            dos.flush();
            dos.writeUTF(kommun);
            tax = dis.readFloat();
        } catch (IOException e) {
        }
        return tax;
    }


}