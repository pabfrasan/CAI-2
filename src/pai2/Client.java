package pai2;
import java.io.*;
import java.math.BigInteger;
import java.net.Socket;
import java.security.MessageDigest;

import javax.net.*;
import javax.swing.JOptionPane;

public class Client {

	////////////////////////////////////////////////////////////////////////////////
	// Instance fields

	/**
	 * Key used to generate the MACs.
	 */
	private String key;
	/**
	 * Algorithm used to generate the MACs.
	 */
	private MessageDigest algorithm;

	////////////////////////////////////////////////////////////////////////////////
	// Instance initializers

	/**
	 * Constructs a client and opens a connection.
	 */
	public Client(String key, MessageDigest algorithm) {
		this.key = key;
		this.algorithm = algorithm;
	}

	////////////////////////////////////////////////////////////////////////////////
	// Instance methods

	/**
	 * Attempts to send a message to the server.
	 */
	public void sendMessage(String message) {
		// Socket to communicate with the server
		Socket socket = null;
		// BufferedReader to read from the server
		BufferedReader input = null;
		// PrintWriter to send data to the server
		PrintWriter output = null;
		try {
			SocketFactory socketFactory = (SocketFactory) SocketFactory.getDefault();
			socket = (Socket) socketFactory.createSocket("localhost", 7070);
			input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			output = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
			// Get nonce from the server
			String nonce = input.readLine();
			System.out.println("nonce que recibe el cliente"+nonce);
			// Send message to the server
			output.write(message + "\n");
			output.flush();
			// Calculate the MAC with shared key
			String messageWithoutIntegrity = "aaaaa";
			byte[] messageDigest = algorithm.digest((message+key+nonce).getBytes());
			BigInteger number = new BigInteger(1, messageDigest);
			String messageMAC = number.toString(16);
//			String messageMAC = Util.fromByteArray(algorithm.digest((message + key + nonce).getBytes()));
			System.out.println("Cliente "+messageMAC);
			output.write(messageMAC + "\n");
			output.flush();
			// Flush operations to send messages correctly
			output.flush();
			// Read response from the server
			String response = input.readLine();
			// Show the response in the client
			JOptionPane.showMessageDialog(null, response);
			// Closing connections
		} catch (IOException ioException) {
			ioException.printStackTrace();
		} finally {
			try {
				input.close();
				output.close();
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.exit(0);
		}
	}

}
