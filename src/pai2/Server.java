package pai2;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import javax.net.ServerSocketFactory;

public class Server {

	// Clave compartida entre cliente y servidor
	private String key;
	
	// Algoritmo para generar la MAC
	private MessageDigest algorithm;

	// Generador random para crear nonces
	private Random random;

	// Se guardan aquí las nonces y se comprueba que no es repetida, para evitar ataques replay
	private Set<Long> nonces;

	
	public Server(String key, MessageDigest algorithm) throws IOException {
		this.key = key;
		this.algorithm = algorithm;
		try {
			this.random = SecureRandom.getInstanceStrong();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		this.nonces = new HashSet<Long>();
	}

	// Creador de nonce
	private String createNonce() {
		Long nonce;
		do {
			nonce = this.random.nextLong();
		} while (this.nonces.contains(nonce));
		return Util.fromLong(nonce);
	}

	
	public void getMessage() {
	
		ServerSocket serverSocket = null;
		Socket socket = null;
		BufferedReader input = null;
		PrintWriter output = null;
		try {
			ServerSocketFactory serverSocketFactory = (ServerSocketFactory) ServerSocketFactory.getDefault();
			// Crear ServerSocket listening at port 7070
			serverSocket = (ServerSocket) serverSocketFactory.createServerSocket(7070);
			System.err.println("Esperando conexiones...");
			socket = (Socket) serverSocket.accept();
			input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			output = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
			// Generamos el nonce
			String nonce = this.createNonce();
			output.write(nonce + "\n");
			output.flush();
			// Leemos el mensaje del cliente
			String message = input.readLine();
			// Se genera la MAC con la clave, el mensaje y el nonce
//			byte[] messageDigest = algorithm.digest((message+key+nonce).getBytes());
//			BigInteger number = new BigInteger(1, messageDigest);
//			String computedMessageMAC = number.toString(16);
			String serverMessageMAC = Util.fromByteArray(algorithm.digest((message + key + nonce).getBytes()));
			// MAC comprobamos que la MAC que nos envia el cliente corresponde a la calculada por el servidor
			String clientMessageMAC = input.readLine();
			if (clientMessageMAC.equals(serverMessageMAC)) {
				output.write("Mensaje enviado con Integridad.\n");
			} else {
				output.write("Mensaje enviado sin Integridad.\n");
			}
			output.flush();
		} catch (IOException exception) {
			exception.printStackTrace();
		} finally {
			try {
				input.close();
				output.close();
				socket.close();
				serverSocket.close();
			} catch (Exception exception) {
				exception.printStackTrace();
			}
		}
	}

}
