
package com.example.i7.jobbalagom.java.remote;

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
import java.sql.SQLException;
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
			connectDB();
		}

		/*
         * Handles diffrent questions from client.
         */
		public void run() {
			while (connected) {
				try {
					System.out.println("Waiting on message from Client");
					int intOperation = dis.readInt();
					System.out.println(intOperation + " Operation");
					switch (intOperation) {

						case 1://getKommun
							ArrayList<String> list = new ArrayList<String>();
							list.add("ay");
							list.add("lmao");
							list.add("mofo");
						//	oos.writeObject(getKommun());
							break;

						case 2://getCity
							dos.writeUTF(getCity(dis.readUTF()));
							dos.flush();
							break;

						case 3://getTax
							dos.writeFloat(getTax(dis.readUTF()));
							break;
					}
				} catch (IOException e) {
					System.out.println("[ERROR] IOException or SQLException");
					connected=false;
				} //catch (SQLException e) {
					// TODO Auto-generated catch block
				//	e.printStackTrace();
				//}
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
	/*
	//Should read all kommun strings from table skatt16april
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
	*/
	private String getCity(String choosenKommun) {
		String query = "select Ort" + "from " + "ad8284" + ".skatt16april" + "where " + choosenKommun;
		System.out.println(query);
		return query;
	}

	private float getTax(String choosenOrt) {
		String query = "select Summa" + "from " + "ad8284" + ".skatt16april" + "where " + choosenOrt;
		return Float.parseFloat(query);
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