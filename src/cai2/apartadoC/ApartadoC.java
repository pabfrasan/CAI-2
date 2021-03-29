package cai2.apartadoC;

import java.io.IOException;

public class ApartadoC {

	public static void main(String[] args) throws IOException {
		testNoHandler();
		testHandler();
		System.exit(0);
	}

	private static void testNoHandler(){
		System.out.println("PROBANDO LA CONEXIÓN SIN TENER UN HANDLER");
		Integer port = 8082;
		Server server = new Server(port);
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

		} catch (Exception e) {
			System.out.println("Principal exception: "+ e.getMessage());
			server.close();
			System.exit(1); //Exit the system with failure status
		}
	}

	private static void testHandler() throws IOException{
		System.out.println("PROBANDO LA CONEXIÓN DESPUES DE TENER UN HANDLER");
		Integer port = 8083;
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
		} catch (Exception e) {
			System.out.println("Principal exception: "+ e.getMessage());
			server.close();
			System.exit(1); //Exit the system with failure status
		}
	}
}
