package com.example.i7.jobbalagom;

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
	
	public Server(int port){
		try {
			serverSocket = new ServerSocket(port);
		}catch (IOException e){

		}
		start();
	}
	
	public void run(){
		while(true){
			try {
				Socket socket = serverSocket.accept();
				new ClientHandeler(socket).start();
			} catch (IOException e) {}
		}
	}
	
	private class ClientHandeler extends Thread{
		
		private Socket socket;
		private DataInputStream dis;
		private DataOutputStream dos;
		
		public ClientHandeler(Socket socket){
			this.socket = socket;
			try {
				dis = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
				dos = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
			} catch (IOException e) {}
		}
		
		public void run(){
			while(true){
				try {
					dis.readInt();
					dos.writeUTF("Från servern");
				}catch (IOException e){

				}
			}
		}
		
	}
}
