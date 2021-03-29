package cai2.apartadoC;
import java.io.IOException;

import java.net.ServerSocket;
public class MultiServer extends Thread {


	private Integer port = 8082;
	private ServerSocket serverSocket;

	public MultiServer(Integer port) {
		this.port = port;
	}

	public void run() {
		try {
			serverSocket = new ServerSocket(port);
			while (!serverSocket.isClosed())
				new ClientHandler(serverSocket.accept()).start();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				serverSocket.close();
				interrupt();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void close() throws IOException {
		this.serverSocket.close();
	}
}