package cai2.apartadoC;

import java.io.IOException;

public class ApartadoC {

	public static void main(String[] args) throws IOException {
		Integer port = 8082;
		MultiServer server = new MultiServer(port);
		Client client1 = new Client(port);
		Client client2 = new Client(port);
		try {
			server.start();
			Thread.sleep(1000);
			client1.sendMessage("Soy el cliente 1");
			client2.sendMessage("Soy el cliente 2");
			Thread.sleep(10000);
			server.close();
			System.out.println("Server stopped");
			System.exit(0); //Exit the system with success status
		} catch (Exception e) {
			System.out.println("Principal exception: "+ e.getMessage());
			server.close();
			System.exit(1); //Exit the system with failure status
		}
	}
}
