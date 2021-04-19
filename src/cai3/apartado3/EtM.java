package cai3.apartado3;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import cai3.apartado3.cifrados.Chacha20;

/*
 * Ciframos el texto plano
 * Hacemos hashing sobre el cifrado
 * Enviamos el texto cifrado y el hash
 */
public class EtM {

public static void main(String[] args) throws Exception {

	Random random = ThreadLocalRandom.current();
	byte[] r = new byte[32]; //Creamos la clave
	random.nextBytes(r);
	
	byte[] t = new byte[500000]; //Creamos un texto en claro
	random.nextBytes(t);
	
	String s = new String(r);
	String texto = new String(t);
	
	
	Chacha20 c = new Chacha20(s);
	Chacha20 c1 = new Chacha20(s);
	String text = "Esto es un texto de prueba";
	System.out.println("-------------- Para entradas de 500 Kb ------------------");
	long inicio = System.currentTimeMillis();
	byte[] cifrado = c.encrypt(texto);
	long enc = System.currentTimeMillis();
	System.out.println("Encriptar con ChaChaPoly : \nTiempo: "+(enc-inicio)+"ms");
	String des = c1.decrypt(cifrado);
	long desc = System.currentTimeMillis();
	System.out.println("Desencriptar con ChaChaPoly: \nTiempo: "+(desc-enc)+"ms");
	
	System.out.println("Tiempo total: "+(desc-inicio)+"ms");
	System.out.println("Texto en claro: "+texto.subSequence(0, 100)+" ...");
	System.out.println("Texto descifrado "+des.subSequence(0, 100)+" ...");
	}
}
