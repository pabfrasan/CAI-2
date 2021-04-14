package pai2;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.swing.JOptionPane;
public class Main {
    public static void main(String[] args) throws IOException {
		try {
			String key = JOptionPane.showInputDialog(null, "Introduzca la clave:");
			String algorithmName = JOptionPane.showInputDialog(null, "Introduzca el algoritmo:");
			MessageDigest algorithm = MessageDigest.getInstance(algorithmName);
			String message = JOptionPane.showInputDialog(null, "Introduzca la cuenta origen");
			Server server = new Server(key, algorithm);
			Client client = new Client(key, algorithm);
			Thread clientThread = new Thread(() -> client.sendMessage(message));
			Thread serverThread = new Thread(() -> server.getMessage());
			clientThread.start();
			serverThread.start();
		} catch (NoSuchAlgorithmException exception) {
			exception.printStackTrace();
		}
	}
}
