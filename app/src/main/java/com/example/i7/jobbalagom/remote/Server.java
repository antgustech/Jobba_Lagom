package com.example.i7.jobbalagom.remote;
/**
 * Created by Anton, Christoffer, Kajsa, Jakup och Morgan
 */
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Server extends Thread {

    private ServerSocket serverSocket;
    private Connection dbConnection;
    private boolean connected = false;
    private final int PING = 0;
    private final int GETMUNICIPALITIES = 1;
    private final int GETCHURCHTAX = 2;
    private final int GETTAX = 3;
    private final int PORT = 6666;

    /**
     * Connects to database and starts a thread to start the connection
     */

    public Server() {
        System.out.println("Server waiting to establish connection");
        connectDB();
        try {
            serverSocket = new ServerSocket(PORT);
            connected = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        start();
    }

    /**
     * Main method that creates an instance of server.
     * @param args arguments from the command line.
     */

    public static void main(String[] args) {
        Server server = new Server();
    }

    /**
     * Thread responsible for establishing connection to the client.
     */

    public void run() {
        while (true) {
            Socket socket;
            try {
                socket = serverSocket.accept();
                System.out.println("Sever is running.");
                new ClientHandeler(socket).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Clienthandler is used for handling multiple clients at the same time.
     */

    private class ClientHandeler extends Thread {
        private Socket socket;
        private DataInputStream dis;
        private DataOutputStream dos;
        private ObjectOutputStream oos;

        public ClientHandeler(Socket socket) {
            connected = true;
            this.socket = socket;
            try {
                dis = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
                dos = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
                oos = new ObjectOutputStream(socket.getOutputStream());
            } catch (IOException e) {
                System.out.println("[ERROR] in stream creation");
            }
            System.out.println("Client connected to server");
        }

        /**
         * This thread is started for each client and waits for an integer so choose which operation to execute.
         * @throws IOException if there is a problem reading from streams.
         */

        public void run() {
            while (connected) {
                try {
                    System.out.println("Waiting on message from Client");
                    int intOperation = dis.readInt();
                    System.out.println(intOperation + " Operation");
                    switch (intOperation) {
                        case PING:
                            dos.writeInt(1);
                            dos.flush();
                            System.out.println("Server pinged");
                            break;

                        case GETMUNICIPALITIES:
                            oos.writeObject(getMunicipality());
                            oos.flush();
                            break;

                        case GETCHURCHTAX:
                            String municipality = dis.readUTF();
                            float tax = getChurchTax(municipality);
                            dos.writeFloat(tax);
                            dos.flush();
                            break;

                        case GETTAX:
                            dos.writeFloat(getTax(dis.readUTF()));
                            dos.flush();
                            break;
                    }
                } catch (IOException | SQLException e) {
                    System.out.println("[ERROR] in switch");
                    connected = false;
                }
            }
        }
    }

    /**
     * Connects to the database.
     * @throws ClassNotFoundException if jdbc cannot be found
     * @throws SQLException if there is a problem with the db.
     */

    private void connectDB() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            dbConnection = DriverManager.getConnection("jdbc:mysql://195.178.232.16:3306/ad8284", "ad8284", "hejsan55");
            System.out.println("Server connected to database");
        } catch (ClassNotFoundException | SQLException e1) {
            System.out.println("[ERROR] Problem with connecting to db");
        }
    }

    /**
     * Disconnects from database
     * @throws SQLException if there is a problem connecting to database.
     */

    private void disconnectDB() {
        try {
            dbConnection.close();
        } catch (SQLException e) {
            System.out.println("[ERROR] Problem with disconnecting db");
        }
    }

    /**
     * Retrives municipalities and sends them to the client.
     * @return ArrayList contaning the distinct municipalities from the database.
     * @throws SQLException if there is a problem with the db.
     */

    private ArrayList<String> getMunicipality() throws SQLException {
        ArrayList<String> names = new ArrayList<String>();;
        try {
            Statement s =  dbConnection.createStatement();
            ResultSet rs = s.executeQuery("select distinct Kommun from skatt16april order by Kommun");
            names = new ArrayList<String>();
            while (rs.next()) {
                names.add(rs.getString(1));
            }
            System.out.println("Skriver ut kommuner");
        } catch (SQLException e) {
            System.out.println("[ERROR] in getMunicipalities");
        }
        return names;
    }

    /**
     * Retrives the average churchTax from server.
     * @param municipalities The municipality choosen by the user.
     * @return float containing the average churchTax from the choosen municipality.
     * @throws SQLException if there is a problem with the db.
     */

    private float getChurchTax(String municipalities) {
        float tax = 0f;
        try {
            Statement s = dbConnection.createStatement();
            ResultSet rs = s.executeQuery("select AVG(SummaInkluderatKyrkan) FROM skatt16april WHERE kommun='" + municipalities + "';");
            int columnCount = rs.getMetaData().getColumnCount();
            while (rs.next()) {
                for (int i=0; i <columnCount ; i++)
                {
                    tax = Float.parseFloat( rs.getString(i + 1));
                }
            }
        } catch (SQLException e) {
            System.out.println("[ERROR] in getChurchTax");
        }
        System.out.println("ChurchTax requested: " + tax);
        return tax;
    }

    /**
     * Retrives the average Tax from server.
     * @param choosenKommun The municipality choosen by the user.
     * @return float containing the average Tax from the choosen municipality.
     * @throws SQLException if there is a problem with the db.
     */

    private float getTax(String choosenKommun) {
        float tax = 0f;
        try {
            Statement s = dbConnection.createStatement();
            ResultSet rs = s.executeQuery("select AVG(SummanExkluderatKyrkan) FROM skatt16april WHERE kommun='" + choosenKommun + "';");
            int columnCount = rs.getMetaData().getColumnCount();
            while (rs.next()) {
                for (int i=0; i <columnCount ; i++)
                {
                    tax = Float.parseFloat( rs.getString(i + 1));
                }
            }
        } catch (SQLException e) {
            System.out.println("[ERROR] in getTax");
        }
        System.out.println("Tax requested: " + tax);
        return tax;
    }
}