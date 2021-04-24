package pai3;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.security.SecureRandom;


public class BYODGenerateUsers {

    public static void main(String[] args) throws IOException {
        File archivo = new File(System.getProperty("user.dir")+"/src/pai3/user.txt");
        BufferedWriter bufferWritter = new BufferedWriter(new FileWriter(archivo));
        String easy = RandomString.alphanum + "@-_&%?!¿¡";
    	for (int i = 0; i < 300; i++) {
    		RandomString tickets = new RandomString(23, new SecureRandom(), easy);
    		bufferWritter.write("user"+i + "," + tickets.nextString() + "\n");            	
		}
    	bufferWritter.close();
    }
}
