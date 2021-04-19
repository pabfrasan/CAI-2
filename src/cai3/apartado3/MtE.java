package cai3.apartado3;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Provider;
import java.security.Security;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import cai3.apartado3.cifrados.AES;



/*
 * Hacemos hashing sobre el texto en claro y añadimos la MAC al texto en claro.
 * Luego ciframos el resultado.
 */
public class MtE {
/*
 * cipher suit: ChaCha20Poly1305, AES-GCM, AES-CCM
 */
	 public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeyException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException, NoSuchProviderException {
	    
		AES cliente = new AES("una clave de 16 bits"); //Inicializamos dos recursos para que se comuniquen
		AES servidor = new AES("una clave de 16 bits"); //Usamos la misma clave
		
		Random random = ThreadLocalRandom.current();
		byte[] t = new byte[500000]; //Creamos un texto en claro
		random.nextBytes(t);
			
		String texto = new String(t);
		System.out.println("-------------- Para entradas de 500 Kb ------------------");
		long inicio = System.currentTimeMillis();
		byte[] cifrado = cliente.cifrarCCM(texto);
		long enc = System.currentTimeMillis();
		System.out.println("Encriptar con AES-CCM : \nTiempo: "+(enc-inicio)+"ms");
		String des = servidor.descifrarCCM(cifrado);
		long desc = System.currentTimeMillis();
		System.out.println("Desencriptar con AES-CCM: \nTiempo: "+(desc-enc)+"ms");
		
		System.out.println("Tiempo total: "+(desc-inicio)+"ms");
		System.out.println("Texto en claro: "+texto.subSequence(0, 100)+" ...");
		System.out.println("Texto descifrado "+des.subSequence(0, 100)+" ...");
		}
	}

