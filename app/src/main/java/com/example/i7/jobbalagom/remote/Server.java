package com.example.i7.jobbalagom.remote;


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
import java.util.List;

/*TODO hÃ¤mta aktuell skattesats
*SHOULD MAYBE BE REMOVED!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
 */
public class Server extends Thread {

	private ServerSocket serverSocket;
	private float number = 0.5f;
	private Connection dbConnection;
	private ArrayList<String> alKommun;
	private boolean connected = false;

	/*
	 * ------Handles connection
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
				oos = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));



			} catch (IOException e) {
			}
			System.out.println("New device connection" );
			connectDB();
		}
		/*
         * Handles diffrent questions from client.
         */
		public void run() {
			while (connected) {
				try {
					int intOperation = dis.readInt();
					switch (intOperation) {

						case 1://getKommun
							oos.writeObject(getKommun());
							dos.flush();
							break;

						case 2://getCity
							dos.writeUTF(getCity(dis.readUTF()));
							dos.flush();
							break;

						case 3://getTax
							dos.writeFloat(getTax(dis.readUTF()));
							break;
					}
				} catch (IOException | SQLException e) {
					e.printStackTrace();
					connected=false;

				}
			}
		}
	}

	/*
	 * --------DB methods--------------
	 */

	private void connectDB() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			dbConnection = DriverManager.getConnection("jdbc:mysql://195.178.232.7:4040/ad8284", "ad8284", "hejsan55");
			System.out.println("Connected to database");
		} catch (ClassNotFoundException e1) {
		} catch (SQLException e2) {
			System.out.println("Problem with connecting to db");
		}
	}

	private void disconnectDB() {
		try {
			dbConnection.close();
		} catch (SQLException e) {
			System.out.println("Problem with disconnecting db");
		}
	}
	//Should read all kommun strings from table skattapril16
	private List<String> getKommun() throws SQLException {
	//	String kommuner ="";
//		String query = "select Kommun" + "from " + "ad8284" + "skattapril16";

	//	try (Statement s = (Statement) dbConnection.createStatement()) {
			//try (ResultSet rs = s.executeQuery("select distinct Kommun from skattapril16 order by Kommun")) {
			//	List<String> names = new ArrayList<String>();

			//	while (rs.next()) {
			//		names.add( rs.getString(1));
			//		System.out.println(rs.getString(1));
			//	}

			//	return names;
		//	}
		return null;
	}
	//}

	private String getCity(String choosenKommun) {
		String query = "select Ort" + "from " + "ad8284" + ".skattapril16" + "where " + choosenKommun;
		System.out.println(query);
		return query;
	}

	private float getTax(String choosenOrt) {
		String query = "select Summa" + "from " + "ad8284" + ".skattapril16" + "where " + choosenOrt;
		return Float.parseFloat(query);
	}

}