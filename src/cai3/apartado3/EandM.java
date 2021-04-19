package cai3.apartado3;
/*
 * Se cifra el texto en claro y también se hace hashing sobre el texto claro
 * Luego se envia el cifrado y el hash
 */

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import cai3.apartado3.cifrados.AES;


public class EandM {
	public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeyException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
		AES aes =new AES("una clave de 16 bytes");
		AES aes1  = new AES("una clave de 16 bytes");
		Random random = ThreadLocalRandom.current();
		String textplain = "Esto es una prueba";
		long inicio1 = System.currentTimeMillis();
		byte[] aesEnc = aes.cifrarGCM(textplain);
		long enc1 = System.currentTimeMillis();
		System.out.println("Encriptación de AES-GCM :"+"Texto a cifrar: "+textplain+" \nTiempo: "+ (enc1-inicio1));
		String aesDes = aes1.descifrarGCM(aesEnc);
		long denc1 = System.currentTimeMillis();
		System.out.println("Descifrado de AES-GCM :"+aesDes+ " \nTiempo: "+((denc1-enc1)));
		System.out.println("Tiempo total: "+(denc1-inicio1));
		
		System.out.println("-------------- Para entradas de 500 Kb ------------------");
		byte[] r = new byte[500000]; //Means 2048 bit
		random.nextBytes(r);
		String s = new String(r);
		String textplain1 =  new String(r);
		long inicio2 = System.currentTimeMillis();
		byte[] aesEnc2 = aes.cifrarGCM(textplain1);
		long enc2 = System.currentTimeMillis();
		System.out.println("Encriptación de AES-GCM : \nTiempo: "+ (enc2-inicio2)+"ms");
		String aesDes2 = aes1.descifrarGCM(aesEnc2);
		long denc2 = System.currentTimeMillis();
		System.out.println("Descifrado de AES-GCM : \nTiempo: "+((denc2-enc2))+"ms");
		System.out.println("Tiempo total: "+(denc2-inicio2)+"ms");
		System.out.println("Texto en claro: "+textplain1.subSequence(0, 100)+" ...");
		System.out.println("Texto descifrado "+aesDes2.subSequence(0, 100)+" ...");
		
		
	}

}
