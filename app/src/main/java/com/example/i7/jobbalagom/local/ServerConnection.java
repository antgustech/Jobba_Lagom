package com.example.i7.jobbalagom.local;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by Strandberg95 on 2016-03-30.
 */
public class ServerConnection extends Thread {
    private Socket socket;
    private DataInputStream dis;

   // private DataOutputStream dos;
    private MessageCallback messageCallback;

    private String IP;
    private int PORT;

    public ServerConnection(MessageCallback messageCallback, String ip,int port){
        this.IP = ip;
        this.PORT = port;
        this.messageCallback = messageCallback;
        start();
    }

    public void connect(String ip, int port){
        try{
            socket = new Socket(ip,port);
            dis = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            //dos = new DataOutputStream((new BufferedOutputStream(socket.getOutputStream())));
        }catch(IOException e){}
    }

    public void closeConnection(){
        Thread.interrupted();
    }

    public void run(){
        boolean done = false;
        connect(IP, PORT);
        while(!done){
            try{
                messageCallback.updateMessage(dis.readFloat());
                done = true;
            }catch(IOException e){}
        }
        closeConnection();
    }


}