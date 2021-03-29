package cai2.apartadoC;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.net.SocketFactory;
public class Client extends Thread {
	private int port = 8082;
	private String hostname = "localhost";
	private BufferedReader in;
	private String text;
	private Socket clientSocket;
	private PrintWriter out;

	public Client(Integer port) {
		this.port = port;
	}
	@Override
	public void run() {
		SocketFactory socketFactory = SocketFactory.getDefault();
		try {
			clientSocket = socketFactory.createSocket(hostname, port);
			out = new PrintWriter(clientSocket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

			out.println(text);
			out.flush();

			String response = in.readLine();
			System.out.println(response);

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

	public void sendMessage(String text) {
		this.text = text;
		this.start();
	}

	public void stopConnection() {
		try {
			this.in.close();
			this.out.close();
			this.clientSocket.close();
			this.interrupt();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
}