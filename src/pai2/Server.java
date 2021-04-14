package pai2;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
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
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import javax.net.ServerSocketFactory;

public class Server {

	// Clave compartida entre cliente y servidor
	private String key;
	
	// Algoritmo para generar la MAC
	private MessageDigest algorithm;

	// Generador random para crear nonces
	private Random random;

	// Se guardan aqu� las nonces y se comprueba que no es repetida, para evitar ataques replay
	private Set<Long> nonces;

	File file = new File("logNoIntegrity.txt");
	File file2 = new File("logIntegrity.txt");
	File file3 = new File("kpi.txt");
	FileWriter fw = null;
	BufferedWriter bw = null;
	FileReader f=null;
	BufferedReader rs = null;

	private float numMens=0;
	private float mensInteg =0;
	private float kpi =0;
          


	
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
		Long nonce = this.random.nextLong();
		while (this.nonces.contains(nonce)){
			nonce = this.random.nextLong();
		}
		return String.format("%016x", nonce);
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

				//Escribo el log con los mensajes enviados con integridad
				try {
					if (!file2.exists()) {
						try {
							file2.createNewFile();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
					fw = new FileWriter(file2.getAbsoluteFile(), true);
					bw = new BufferedWriter(fw);
				} catch (IOException e) {
					e.printStackTrace();
				}
				bw.write("mensaje: "+ message + " ha llegado de forma integra\n");
				bw.close();
				fw.close();
				//End escribir log
			} else {
				//Escribo log de mensajes sin integridad
				try {
					if (!file.exists()) {
						try {
							file.createNewFile();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
					fw = new FileWriter(file.getAbsoluteFile(), true);
					bw = new BufferedWriter(fw);
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				output.write("Mensaje enviado sin Integridad.\n");
				bw.write("mensaje: "+ message + " no han llegado de forma integra\n");
				bw.close();
				//End escribir log
			}
			
			//Cálculo de kpi
			if (!file.exists()){

			}else{
				f = new FileReader("logNoIntegrity.txt");
      
				rs = new BufferedReader(f);
				numMens = (int)rs.lines().count();
				rs.close();
			}
			if (!file2.exists()){

			}else{
				f = new FileReader("logIntegrity.txt");
				rs = new BufferedReader(f);
				mensInteg=(int)rs.lines().count();
				numMens += mensInteg;
				kpi = mensInteg;	
				rs.close();
			}
			kpi = mensInteg/numMens;
			//End cálculo kpi

			//Escribo fichero con kpi
			if (file3.exists()){
				file3.delete();
				file3= new File("kpi.txt");
			}
			fw = new FileWriter(file3.getAbsoluteFile(), true);
			bw = new BufferedWriter(fw);
			
			bw.write("mensajes totales:" +numMens + "\n" );
			bw.write("kpi:" +kpi + "\n" );
			//End escribir fickero kpi

			try {
				//Cierra instancias de FileWriter y BufferedWriter
			if (bw != null)
				bw.close();
			if (fw != null)
				fw.close();
			} catch (IOException ex) {
				ex.printStackTrace();
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
