package cai3.apartado3.cifrados;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.Security;
import java.util.Random;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;


import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class AES {

	private Key key;
	
	private byte[] iv;
	
	private GCMParameterSpec gcm;	
	

	
	public AES(String key) throws NoSuchAlgorithmException{
	   KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
	   keyGenerator.init(128);
	   Key aux = keyGenerator.generateKey();
	   aux = new SecretKeySpec(key.getBytes(),  0, 16, "AES");
	   
	   this.key = aux;
	   
	   this.iv = new byte[12];
	   Random random = new Random();
	   random.setSeed(key.hashCode());
	   random.nextBytes(this.iv);
		
		this.gcm = new GCMParameterSpec(16 * 8, this.iv);
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

	}

	public Key getKey() {
		return key;
	}

	public void setKey(Key key) {
		this.key = key;
	}
	
	
	public byte[] cifrar(String mensaje) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		Cipher aes = Cipher.getInstance("AES/ECB/PKCS5Padding");
		aes.init(Cipher.ENCRYPT_MODE, key);
		return  aes.doFinal(mensaje.getBytes());
		}
	public String descifrar(byte[] enc) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		Cipher aes = Cipher.getInstance("AES/ECB/PKCS5Padding");
	    aes.init(Cipher.DECRYPT_MODE, key);
	    byte[] desencriptado = aes.doFinal(enc);
		return new String(desencriptado);
		
	}
	
	public byte[] cifrarGCM(String mensaje) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
		Cipher aes = Cipher.getInstance("AES/GCM/NoPadding");
		aes.init(Cipher.ENCRYPT_MODE, key, this.gcm);    
		return  aes.doFinal(mensaje.getBytes());

		}
	
	public String descifrarGCM(byte[] enc) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
		Cipher aes = Cipher.getInstance("AES/GCM/NoPadding");
	    aes.init(Cipher.DECRYPT_MODE, key,this.gcm);
	    byte[] desencriptado = aes.doFinal(enc);
		return new String(desencriptado);
		
	}
	
	public byte[] cifrarCCM(String mensaje) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException, NoSuchProviderException {
		Cipher aes = Cipher.getInstance("AES/CCM/NoPadding", "BC");
		
		aes.init(Cipher.ENCRYPT_MODE, key,this.gcm);    
		return  aes.doFinal(mensaje.getBytes());

		}
	
	public String descifrarCCM(byte[] enc) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException, NoSuchProviderException {
		Cipher aes = Cipher.getInstance("AES/CCM/NoPadding", "BC");
	    aes.init(Cipher.DECRYPT_MODE, key,this.gcm);
	    byte[] desencriptado = aes.doFinal(enc);
		return new String(desencriptado);
		
	}

}

