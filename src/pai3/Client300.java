package pai3;

import java.awt.desktop.ScreenSleepEvent;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.time.Duration;
import java.time.Instant;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

public class Client300 {

    private static final String[] protocols = new String[]{"TLSv1.3"};
    private static final String[] cipher_suites = new String[]{"TLS_AES_128_GCM_SHA256"};
    
    public static void BYODClient2(String user, String pass, String message) {
        try {
            SSLSocketFactory socketFactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
            SSLSocket socket = (SSLSocket) socketFactory.createSocket("localhost", 7070);
            socket.setEnabledProtocols(protocols);
            socket.setEnabledCipherSuites(cipher_suites);
            // Crea un PrintWriter para enviar mensaje/MAC al servidor
            PrintWriter output = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));


            output.println(user);
            output.println(pass);
            output.println(message);

            // Importante para que el mensaje se envíe
            output.flush();
            // Crea un objeto BufferedReader para leer la respuesta del servidor
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            // Lee la respuesta del servidor
            String respuesta = input.readLine();
            // Muestra la respuesta al cliente
            System.out.println(respuesta);
            // Se cierra la conexion
            output.close();
            input.close();
            socket.close();
        } // end try
        catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
    

    // ejecución del cliente de verificación de la integridad
    public static void main(String args[]) throws IOException, InterruptedException {
    	System.out.println(System.getProperty("user.dir"));
        String cadena;
        FileReader f = new FileReader("user.txt");
        BufferedReader b = new BufferedReader(f);
        int i = 1;

        Instant start = Instant.now();

        while((cadena = b.readLine())!=null) {
            System.out.println(cadena);
            BYODClient2(cadena.split(",")[0],cadena.split(",")[1],"Mensaje del usuario "+i);

        }
        b.close();
    
    	

    	Instant end = Instant.now();
    	Duration interval = Duration.between(start, end);
    	System.err.println("Execution time in seconds: " + 	interval.getSeconds());
    }

}
