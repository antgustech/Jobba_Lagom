package jobbalagom.remote;
/*
 * Created by Anton 15-04-16
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

    /*
     * ------Handles connection-----------
     */
    public Server() {
        System.out.println("Server waiting to establish connection");
        try {
            serverSocket = new ServerSocket(4545);
            connected = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        start();
    }

    public static void main(String[] args) {
        Server server = new Server();

    }

    public void run() {
        while (true) {
            Socket socket;
            try {
                socket = serverSocket.accept();
                System.out.println("Sever is running.");
                new ClientHandler(socket).start();
            } catch (IOException e) {
            }
        }
    }

    private class ClientHandler extends Thread {
        private Socket socket;
        private DataInputStream dis;
        private DataOutputStream dos;
        private ObjectOutputStream oos;

        public ClientHandler(Socket socket) {
            connected = true;
            this.socket = socket;
            try {
                dis = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
                dos = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
                oos = new ObjectOutputStream(socket.getOutputStream());
            } catch (IOException e) {
            }
            System.out.println("Client connected to server");
            connectDB();
        }

        /*
         * Gets requests from client and responds
         */

        public void run() {
            while (connected) {
                try {
                    System.out.println("Waiting on message from Client");
                    int operation = dis.readInt();
                    System.out.println(operation + " Operation");
                    switch (operation) {

                        case 1:	//getKommun
                            oos.writeObject(getMunicipalities());
                            break;

                        case 2:	//getCity
                            dos.writeUTF(getCity(dis.readUTF()));
                            dos.flush();
                            break;

                        case 3:	//getTax
                            String municipality = dis.readUTF();
                            float tax = getTax(municipality);
                            dos.writeFloat(tax);
                            dos.flush();
                            break;
                    }
                } catch (IOException e) {
                    System.out.println("[ERROR] IOException or SQLException");
                    connected = false;
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /*
     * ----------------------------------DB methods-----------------------------------
     */

    private void connectDB() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            dbConnection = DriverManager.getConnection("jdbc:mysql://195.178.232.7:4040/ad8284", "ad8284", "hejsan55");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Server connected to database");

    }

    private void disconnectDB() {
        try {
            dbConnection.close();
        } catch (SQLException e) {
            System.out.println("[ERROR] Problem with disconnecting db");
        }
    }

    //Reads all municipalities from table skatt16april
    private ArrayList<String> getMunicipalities() throws SQLException {
        Statement s = (Statement) dbConnection.createStatement();
        ResultSet rs = s.executeQuery("select distinct Kommun from skatt16april order by Kommun");
        ArrayList<String> municipalities = new ArrayList<String>();
        System.out.println("Reading municipalities from database");
        while (rs.next()) {
            municipalities.add(rs.getString(1));
        }
        return municipalities;
    }

    private String getCity(String choosenKommun) {
        String query = "select Ort" + "from " + "ad8284" + ".skatt16april" + " where " + choosenKommun;
        System.out.println(query);
        return query;
    }

    private float getTax(String municipality) {
        String query = "select distinct SummanExkluderatKyrkan" + " from " + "ad8284" + ".skatt16april" + " where " + " kommun= " + "'" + municipality + "'";

        Statement s = null;
        ResultSet rs = null;
        try {
            s = (Statement) dbConnection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Reading tax from database");
        try {
            rs = s.executeQuery(query);
            while (rs.next()) {
                return rs.getFloat("SummanExkluderatKyrkan");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0f;
    }
}


//--------------------------------------------------------------------------------------------------------------------
// skatt16april
//Kommun VARCHAR(15)
//Ort VARCHAR(36)
//SummaInkluderatKyrkan NUMERIC(5, 3)
//SummanExkluderatKyrkan NUMERIC(5, 3)
//PRIMARY KEY (Kommun, Ort)
//select AVG(SUmmaInkluderatKyrkan) from skatt16april where kommun ="ystad"; för att få ut avg församling skatt
//-----------------------------------------------------------------------------------------------------------------