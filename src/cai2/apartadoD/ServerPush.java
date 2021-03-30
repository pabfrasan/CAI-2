package cai2.apartadoD;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import javax.net.ServerSocketFactory;

public class ServerPush extends Thread{

    private Integer port = 8083;
	private boolean running = true;
	private String text;

	public ServerPush(Integer port) {
		this.port = port;
	}

	@Override
	public void run() {
		System.out.println("Starting Socket");
		while (running) {
			// crea Socket de la factoría
			ServerSocketFactory socketFactory = ServerSocketFactory.getDefault();
			try (ServerSocket serverSocket = socketFactory.createServerSocket(port, 15);
				Socket clientSocket = serverSocket.accept();	
				BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);) {
                System.out.println("Server socket accepted");
				this.text = "Buenos días";
				writer.println(text);
				writer.flush();
				String response = reader.readLine();
				System.out.println(response);
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