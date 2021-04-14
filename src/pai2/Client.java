package pai2;
import java.io.*;
import java.math.BigInteger;
import java.net.Socket;
import java.security.MessageDigest;

import javax.net.*;
import javax.swing.JOptionPane;

public class Client {

	// Clave compartida entre cliente y servidor
	private String key;
	
	// Algoritmo para crear la MAC
	private MessageDigest algorithm;

	
	public Client(String key, MessageDigest algorithm) {
		this.key = key;
		this.algorithm = algorithm;
	}

	
	public void sendMessage(String message) {
		
		Socket socket = null;
		BufferedReader input = null;
		PrintWriter output = null;
		try {
			SocketFactory socketFactory = (SocketFactory) SocketFactory.getDefault();
			socket = (Socket) socketFactory.createSocket("localhost", 7070);
			input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			output = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
			// Conseguimos el nonce del servidor
			String nonce = input.readLine();
			// Enviamos el mensaje al servidor
			output.write(message + "\n");
			output.flush();
			// Calculamos la MAC
//			byte[] messageDigest = algorithm.digest((message+key+nonce).getBytes());
//			BigInteger number = new BigInteger(1, messageDigest);
//			String messageMAC = number.toString(16);
			String clientMessageMAC = Util.fromByteArray(algorithm.digest((message + key + nonce).getBytes()));
			output.write(clientMessageMAC + "\n");
			output.flush();
			
			// Leemos la respuesta del servidor
			String response = input.readLine();
			// Mostramos la respuesta
			JOptionPane.showMessageDialog(null, response);
			// Cerramos conexiones
		} catch (IOException exception) {
			exception.printStackTrace();
		} finally {
			try {
				input.close();
				output.close();
				socket.close();
			} catch (IOException exception) {
				exception.printStackTrace();
			}
			System.exit(0);
		}
	}

}
