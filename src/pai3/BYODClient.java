package pai3;

import java.io.*;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.swing.JOptionPane;


public class BYODClient {

    private static final String[] protocols = new String[]{"TLSv1.3"};
    private static final String[] cipher_suites = new String[]{"TLS_AES_128_GCM_SHA256"};


    public BYODClient() {
        try {
            SSLSocketFactory socketFactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
            SSLSocket socket = (SSLSocket) socketFactory.createSocket("localhost", 7070);
            socket.setEnabledProtocols(protocols);
            socket.setEnabledCipherSuites(cipher_suites);

            PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));

            String username = JOptionPane.showInputDialog(null, "Introduzca el nombre de usuario:");
            out.println(username);

            String password = JOptionPane.showInputDialog(null, "Introduzca la contraseña:");
            out.println(password);

            String message = JOptionPane.showInputDialog(null, "Introduzca el mensaje:");
            out.println(message);

            out.flush();

            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String respuesta = input.readLine();

            JOptionPane.showMessageDialog(null, respuesta);

            out.close();
            input.close();
            socket.close();
        }
        catch (IOException ioException) {
            ioException.printStackTrace();
        }

        finally {
            System.exit(0);
        }
    }

    public static void main(String args[]) {
        new BYODClient();
    }
}