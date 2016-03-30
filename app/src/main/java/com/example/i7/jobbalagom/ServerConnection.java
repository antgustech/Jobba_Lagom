package com.example.i7.jobbalagom;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by Strandberg95 on 2016-03-30.
 */
public class ServerConnection extends Thread {

    private String ip = "10.2.22.133";
    private int port = 4545;
    private Socket socket;
    private DataInputStream dis;
    private DataOutputStream dos;

    public ServerConnection(){
        start();
    }

    public void connect(String ip, int port){

        try{
            socket = new Socket(ip,port);
            dis = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            dos = new DataOutputStream((new BufferedOutputStream(socket.getOutputStream())));
        }catch(IOException e){}

    }

    public String sendMessage(String str){
        try{
            dos.writeUTF(str);
            dos.flush();

            return dis.readUTF();
        }catch(IOException e){}

        return "Could not get Estimates";

    }

    public void run(){
        connect(ip,port);
    }
}
