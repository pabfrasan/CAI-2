package cai2.apartadoC;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;


import javax.net.ServerSocketFactory;

public class Server extends Thread {

	private Integer port = 8082;
	private boolean running = true;

	public Server(Integer port) {
		this.port = port;
	}

	@Override
	public void run() {
		System.out.println("Starting Socket");
		while (running) {
			// crea Socket de la factoría
			ServerSocketFactory socketFactory = ServerSocketFactory.getDefault();
			System.out.println("Waiting conection...");
			try (ServerSocket serverSocket = socketFactory.createServerSocket(port, 14);
				Socket clientSocket = serverSocket.accept();	
				BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);) {
                System.out.println("Server socket accepted");
				String text = "a";
				while (text != null && !text.isEmpty()){
					text = in.readLine();
					out.println("Server: " + text);
				}
			} catch (IOException e) {
				running = false;
				System.out.println("puerto: "+ port + " mensaje: "+e.getMessage());
			}
		}
	}
    public void close() {
		interrupt();
    }

}
