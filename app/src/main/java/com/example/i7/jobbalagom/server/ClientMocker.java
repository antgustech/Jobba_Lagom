package server;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientMocker {
	
	private Socket socket;
	
	public ClientMocker(){
		try {
			socket = new Socket("localhost",4545);
		} catch (UnknownHostException e) {}
		catch (IOException e) {}
	}
	
	public static void main(String[] args){
		new ClientMocker();
		new ClientMocker();
		new ClientMocker();
		new ClientMocker();
		new ClientMocker();
		new ClientMocker();
		
	}

}
