package com.example.i7.jobbalagom.remote;

import android.util.Log;

import com.example.i7.jobbalagom.local.Controller;
import com.example.i7.jobbalagom.local.DataHolder;
import com.example.i7.jobbalagom.local.MessageCallback;

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
        controller  =  DataHolder.getInstance().getController();
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
        Log.d("ServerTag","Connecting");
        try {
            socket = new Socket(IP, PORT);
            Log.d("ServerTag","Socket is up");
            dis = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            Log.d("ServerTag","DataInput is up");
            dos = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            Log.d("ServerTag","DataOutput is up");
            ois = new ObjectInputStream((socket.getInputStream()));
            Log.d("ServerTag","ObjectInput is up");
          //  ArrayList<String> attemptList;
           // attemptList =  getKommun();

            kommuner = getKommunFromServer();

        } catch (IOException e) {}
        catch(ClassNotFoundException e2){}
        //  catch(ClassNotFoundException ex){}
    }

    public ArrayList<String> getKommunFromServer() throws IOException, ClassNotFoundException {
        ArrayList<String> list = new ArrayList<String>();
        try {
            dos.writeInt(1);
            dos.flush();

            Log.d("ServerTag", "Operation sent");
            list = (ArrayList) ois.readObject();
            Log.d("ServerTag","Information received");
        } catch (IOException e) {
            e.printStackTrace();
        }  //return (ArrayList<String>)ois.readObject();
        return list;
    }

    public ArrayList<String> getKommunFromClient(){
        return kommuner;
    }

    public void getCity() {
        try {
            dos.writeInt(2);
            messageCallback.updateCities(dis.readUTF());
        } catch (IOException e) {
        }
    }

    public void getTax(){
        try {
            dos.writeInt(3);
            messageCallback.updateTax(dis.readFloat());
        } catch (IOException e) {
        }
    }

    //private class ServerListener extends Thread {
       // public ServerListener(){

       // }

   // }
}