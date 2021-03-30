package cai2.apartadoD;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientPush extends Thread{
    private int port = 8083;
	private String host = "localhost";
	private Socket clientSocket;

	public ClientPush(Integer port) {
		this.port = port;
	}
	@Override
	public void run() {
		try {
			clientSocket = new Socket(host, port);
			PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);
			BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

			String text = "a";
			while (text != null && !text.isEmpty()){
				text = reader.readLine();
				writer.println("Server says: " + text);
			}			

			interrupt();

		} catch (IOException  e) {
			System.out.println(e.getMessage());
			interrupt();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			interrupt();
			System.exit(1);
		}
	}

	
    
}

