package com.example.i7.jobbalagom.remote;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;



/*TODO hämta aktuell skattesats
 * Skicka in skattesats till Samtliga ClientHandelers
 * När klienter ansluter sig skall skattesatser skickas
 */
public class Server extends Thread {

	private ServerSocket serverSocket;
	private float number = 0.5f;

	public Server() {
		try {

			serverSocket = new ServerSocket(4545);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		start();
	}

	public void run() {
		while (true) {
			Socket socket;
			try {
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

		public void run() {
			while (true) {

				try {
					dos.writeFloat(number);
					dos.flush();
					//(System.out.println("Sent message");
				} catch (IOException e) {
					 System.out.println("Could not send message");
				}
			}
		}
	}
	public static void main(String[] args) {
		Server server = new Server();
	}
}