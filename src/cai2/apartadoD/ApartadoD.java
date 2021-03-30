package cai2.apartadoD;

import java.io.IOException;

public class ApartadoD {

    public static void main(String[] args) throws IOException {
		test();
		System.exit(0);
	}
    
    private static void test(){
		System.out.println("PROBANDO LA CONEXIÓN");
		Integer port = 8083;
		ServerPush server = new ServerPush(port);
		ClientPush client = new ClientPush(port);
		
		try {
			server.start();
            client.start();
			Thread.sleep(1000);
			server.close();
			System.out.println("Server stopped");

		} catch (Exception e) {
			System.out.println("Principal exception: "+ e.getMessage());
			server.close();
			System.exit(1); //Exit the system with failure status
		}
	}
}
