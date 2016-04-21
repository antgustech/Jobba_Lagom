package com.example.i7.jobbalagom.remote;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.Statement;

/*TODO hÃ¤mta aktuell skattesats
 * Skicka in skattesats till Samtliga ClientHandelers
 * NÃ¤r klienter ansluter sig skall skattesatser skickas
 */
public class Server extends Thread {

	private ServerSocket serverSocket;
	private float number = 0.5f;
	private Connection dbConnection;
	private ArrayList<String> alKommun;

	public Server() {
		try {
			serverSocket = new ServerSocket(4545);
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
				connectDB();
				socket = serverSocket.accept();
				System.out.println("connected");
				new ClientHandeler(socket).start();
			} catch (IOException e) {
			}
		}
	}

	private class ClientHandeler extends Thread {
		private Socket socket;
		private DataInputStream dis;
		private DataOutputStream dos;

		public ClientHandeler(Socket socket) {
			this.socket = socket;
			try {
				dis = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
				dos = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
			} catch (IOException e) {
			}
		}
		/*
         * Handles diffrent queris from client.
         */
		public void run() {
			while (true) {
				try {
					String intOperation = dis.readUTF();
					switch (intOperation) {

						case "getKommun":
							dos.writeUTF(getKommun());
							break;

						case "getCity":
							dos.writeUTF(getCity(dis.readUTF()));
							break;

						case "getTax":
							dos.writeFloat(getTax(dis.readUTF()));
							break;
					}
				} catch (IOException | SQLException e) {
					System.out.println("Could not send message");

				}
			}
		}
	}

	/*
	 * --------DB methods
	 */

	private void connectDB() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			dbConnection = DriverManager.getConnection("jdbc:mysql://195.178.232.7:4040/ad8284", "ad8284", "hejsan55");
			System.out.println("Connected to db");
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

	private String getKommun() throws SQLException {
		String kommuner ="";
//		String query = "select Kommun" + "from " + "ad8284" + "skattapril16";

		try (Statement s = (Statement) dbConnection.createStatement()) {
			try (ResultSet rs = s.executeQuery("select name from employees order by id")) {
				List<String> names = new ArrayList<String>();

				while (rs.next()) {
					kommuner += rs.getString(1);
					System.out.println("names");
				}

				return kommuner;
			}
		}
//		System.out.println(query);
//		return query;
	}

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