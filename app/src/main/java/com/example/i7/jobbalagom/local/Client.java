package com.example.i7.jobbalagom.local;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by Strandberg95 on 2016-03-30.
 */
public class Client extends Thread {
    private Socket socket;
    private DataInputStream dis;
    private DataOutputStream dos;
    private Thread clientThread;
    private boolean connected = false;

   // private DataOutputStream dos;
    private MessageCallback messageCallback;

    private String IP;
    private int PORT;

    public Client(MessageCallback messageCallback, String ip, int port){
        this.IP = ip;
        this.PORT = port;
        this.messageCallback = messageCallback;
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
            dos = new DataOutputStream((new BufferedOutputStream(socket.getOutputStream())));
        } catch (IOException e) {

        }

    }


    public String getKommun() throws IOException {
        try {
            dos.writeUTF("getKommun");

        } catch (IOException e) {
            e.printStackTrace();
        }  return (dis.readUTF());
    }

    public void getCity() {
        try {
            dos.writeUTF("getCity");
            messageCallback.updateCities(dis.readUTF());
        } catch (IOException e) {
        }
    }

    public void getTax(){
        try {
            dos.writeUTF("getTax");
            messageCallback.updateTax(dis.readFloat());
        } catch (IOException e) {
        }
    }
}