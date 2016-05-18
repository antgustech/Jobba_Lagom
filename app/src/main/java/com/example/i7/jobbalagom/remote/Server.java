package server;
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
import java.util.ArrayList;

import com.mysql.jdbc.Statement;


public class Server extends Thread {

    private ServerSocket serverSocket;
    private Connection dbConnection;
    private boolean connected = false;

    /*
     * ------Handles connection-----------
     */
    public Server() {
        System.out.println("Server waiting to establish connection");
        connectDB();
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
                new ClientHandeler(socket).start();
            } catch (IOException e) {
            }
        }
    }

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
            }
            System.out.println("Client connected to server" );

        }

        /*
         * Handles different questions from client.
         */
        public void run() {
            while (connected) {
                try {
                    System.out.println("Waiting on message from Client");
                    int intOperation = dis.readInt();
                    System.out.println(intOperation + " Operation");
                    switch (intOperation) {
                    
                    	case 0:
                    		dos.writeInt(1);
                    		System.out.println("Server pinged");
                    		dos.flush();
                    		break;

                        case 1://getKommun
                            oos.writeObject(getKommun());
                            oos.flush();
                            break;

                        case 2://getChurchTax
                        	String kommun = dis.readUTF();
                        	float tax = getChurchTax(kommun);
                            dos.writeFloat(tax);
                            dos.flush();
                            break;

                        case 3://getTax
                            dos.writeFloat(getTax(dis.readUTF()));
                            dos.flush();
                            System.out.println("Tax Sent to Client");
                            
                            break;
                    }
                } catch (IOException e) {
                    System.out.println("[ERROR] File error");
                    //connected=false;
                }
                catch (SQLException e) {
                    System.out.println("[ERROR] Network error");
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
            dbConnection = DriverManager.getConnection("jdbc:mysql://195.178.232.16:3306/ad8284", "ad8284", "hejsan55");
            System.out.println("Server connected to database");
        } catch (ClassNotFoundException e1) {
        } catch (SQLException e2) {
            System.out.println("[ERROR] Problem with connecting to db");
        }
    }

    private void disconnectDB() {
        try {
            dbConnection.close();
        } catch (SQLException e) {
            System.out.println("[ERROR] Problem with disconnecting db");
        }
    }

    //Read all kommun strings from table skatt16april and returns them in a list.
   private ArrayList<String> getKommun() throws SQLException {
        String kommuner ="";
        try (Statement s = (Statement) dbConnection.createStatement()) {
            try (ResultSet rs = s.executeQuery("select distinct Kommun from skatt16april order by Kommun")) {
                ArrayList<String> names = new ArrayList<String>();
                while (rs.next()) {
                    names.add( rs.getString(1));
                }System.out.println("Skriver ut kommuner");
                return names;
            }
        }
    }
    //eturns float of the average tax including churchTax from choosen kommyn.
    private float getChurchTax(String choosenKommun) {
        float tax = 0f;
        try {
            Statement s = (Statement) dbConnection.createStatement();
            ResultSet rs = s.executeQuery("select AVG(SummaInkluderatKyrkan) from skatt16april where kommun='" + choosenKommun + "';");
            int columnCount = rs.getMetaData().getColumnCount();
            while (rs.next()) {
                for (int i=0; i <columnCount ; i++)
                {
                    tax = Float.parseFloat( rs.getString(i + 1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("ChurchTax requested: " + tax);
        return tax;
    }

   // Returns float of the average tax excluding churchTax from choosen kommyn.
    private float getTax(String choosenKommun) {
        float tax = 0f;
        try {
            Statement s = (Statement) dbConnection.createStatement();
            ResultSet rs = s.executeQuery("select AVG(SummanExkluderatKyrkan) from skatt16april where kommun='" + choosenKommun + "';");
            int columnCount = rs.getMetaData().getColumnCount();
            while (rs.next()) {
                for (int i=0; i <columnCount ; i++)
                {
                    tax = Float.parseFloat( rs.getString(i + 1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Tax requested: " + tax);
        return tax;

    }
    
}


//--------------------------------------------------------------------------------------------------------------------
// skatt16april THIS IS HOW THE TABLE LOOKS LIKE!
//Kommun VARCHAR(15)
//Ort VARCHAR(36)
//SummaInkluderatKyrkan NUMERIC(5, 3)
//SummanExkluderatKyrkan NUMERIC(5, 3)
//PRIMARY KEY (Kommun, Ort)
//select AVG(SUmmaInkluderatKyrkan) from skatt16april where kommun ="ystad"; för att få ut avg församling skatt
//-----------------------------------------------------------------------------------------------------------------