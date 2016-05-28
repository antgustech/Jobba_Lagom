package com.example.i7.jobbalagom.remote;

import android.util.Log;

import com.example.i7.jobbalagom.callbacks.UpdateTaxCallback;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by Anton, Christoffer, Jakup, Morgan and Christoffer.
 * Client handles connection to the servern.
 */

public class Client extends Thread {

    private final String IP;
    private final int PORT;
    private DataInputStream dis;
    private DataOutputStream dos;
    private ObjectInputStream ois;
    private boolean connected = false;
    private ArrayList<String> municipalities;
    private float tax;

    /**
     * Initializes ip, port and controller also starts a thread.
     *
     * @param IP   to connect to.
     * @param PORT port to connect to.
     */

    public Client(String IP, int PORT) {
        this.IP = IP;
        this.PORT = PORT;
        start();
    }

    /**
     * Check whenever we are connected the the server or not.
     *
     * @return true if we have a connection.
     */

    public boolean checkConnection() {
        Pinger pinger = new Pinger();
        pinger.start();
        return connected;
    }


    /**
     * Thread that creates new streams
     */

    public void run() {
        try {
            Socket socket = new Socket(IP, PORT);
            dis = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            dos = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            ois = new ObjectInputStream((socket.getInputStream()));
            municipalities = getMunicipalitiesFromServer();
            connected = true;
        } catch (IOException e) {
            connected = false;
        }
    }

    /**
     * Gets the muncipalites stored in this class.
     *
     * @return ArrayList with municipalities.
     */

    public ArrayList<String> getMunicipalities() {
        return municipalities;
    }

    /**
     * Gets municipalites from remote servern.
     */

    private ArrayList<String> getMunicipalitiesFromServer() {
        Thread thread = new Thread(new Runnable() {
            public void run() {
                try {
                    dos.writeInt(1);
                    dos.flush();
                    municipalities = (ArrayList<String>) ois.readObject();
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.setPriority(Thread.MAX_PRIORITY);
        thread.start();
        return municipalities;
    }


    /**
     * Gets the churchTax from remote server.
     *
     * @param municipality the choosen municipality from the user.
     * @param callback     so that this thread can update the tax in the internal database.
     */

    public void getChurchTaxFromServer(final String municipality, final UpdateTaxCallback callback) {
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
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.setPriority(Thread.MAX_PRIORITY);
        thread.start();
    }

    /**
     * Returns tax excluding church tax as a float from remote Server
     *
     * @param municipality the choosen municipality from the user.
     * @param callback     so that this thread can update the tax in the internal database.
     */


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

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
        thread.setPriority(Thread.MAX_PRIORITY);
        thread.start();
    }

    /**
     * Thread for pinging the server to ensure conneciton.
     */

    private class Pinger extends Thread {

        private Socket newSocket;
        private DataInputStream newDis;
        private DataOutputStream newDos;
        private ObjectInputStream newOis;

        public void run() {
            try {
                Log.e("PingTag", "Trying to connect");
                newSocket = new Socket(IP, PORT);
                newDis = new DataInputStream(new BufferedInputStream(newSocket.getInputStream()));
                newDos = new DataOutputStream(new BufferedOutputStream(newSocket.getOutputStream()));
                newOis = new ObjectInputStream((newSocket.getInputStream()));
                newDos.writeInt(0);
                newDos.flush();

                Log.e("PingTag", "Server Pinged");

                int answer = newDis.readInt();

                Log.e("PingTag", answer + "");

                if (municipalities == null) {
                    municipalities = getMunicipalitiesFromServer();
                }
                dis = newDis;
                dos = newDos;
                ois = newOis;
                if (!connected) {
                    connected = true;
                    Log.e("PingTag", "new Connected!");
                }
            } catch (IOException e) {
                connected = false;
                Log.e("PingTag", "Server still down");
            }
        }
    }

}

