package pai3;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import javax.net.ssl.SSLHandshakeException;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;

public class BYODServer {

    private SSLServerSocket serverSocket;
    private static final String[] protocols = new String[]{"TLSv1.3"};
    private static final String[] cipher_suites = new String[]{"TLS_AES_128_GCM_SHA256"};


    public BYODServer() throws Exception {
        SSLServerSocketFactory socketFactory = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
        this.serverSocket = (SSLServerSocket) socketFactory.createServerSocket(7070);
        serverSocket.setEnabledProtocols(protocols);
        serverSocket.setEnabledCipherSuites(cipher_suites);
    }

   
    @SuppressWarnings("unused")
	private void runServer() {
        while (true) {
            try {
                System.err.println("Esperando conexiones de clientes...");
                Socket socket = (Socket) serverSocket.accept();
             
                BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            
                PrintWriter output = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
              
                String username = input.readLine();
                String password = input.readLine();
                String message = input.readLine();
		
            
                Boolean userValid = false;
                FileReader users = new FileReader("user.txt");
                try (BufferedReader br = new BufferedReader(users)) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        if (line.equals(username+","+password)) {
                        	userValid = true;
                        }
                    }
                }
                
                if (!userValid) {
                    output.println("Usuario y contraseña no válidos");
                } else{
                	if (true) {
                        File archivo = new File("msg.txt");
                        BufferedWriter bufferWriter = new BufferedWriter(new FileWriter(archivo));
                        bufferWriter.write(message + "\n");
                        bufferWriter.close();
                		output.println("Mensaje almacenado correctamente");
                	}else { 
                		output.println("El mensaje no ha podido almacenarse");
                	}
                }

                output.close();
                input.close();
                socket.close();
            } catch (SSLHandshakeException exception) {
                System.err.println("Error: " + exception);
            } catch (IOException exception) {
                System.err.println("Error: " + exception);
            }
        }
    }

    public static void main(String args[]) throws Exception {
        BYODServer server = new BYODServer();
        server.runServer();
    }
}
