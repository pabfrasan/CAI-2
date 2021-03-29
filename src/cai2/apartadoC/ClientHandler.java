package cai2.apartadoC;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler extends Thread {
    private Socket clientSocket;

    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
    }

    @Override
    public void run() {
        try (
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);) {
            String text = "a";
            while (text != null && !text.isEmpty()){
                text = in.readLine();
                out.println("Server: " + text);
            }
            clientSocket.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
