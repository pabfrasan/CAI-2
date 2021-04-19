package cai3.apartado3.cifrados;

import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.ChaCha20ParameterSpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class Chacha20 {
	private static final String ENCRYPT_ALGO = "ChaCha20";
	
	private Key key;
	
	private byte[] nonce;

	private IvParameterSpec iv;
	
	

    public Chacha20(String key) throws NoSuchAlgorithmException {
 	   KeyGenerator keyGenerator = KeyGenerator.getInstance("ChaCha20");
 	   keyGenerator.init(256);
 	   Key aux = keyGenerator.generateKey();
 	   aux = new SecretKeySpec(key.getBytes(),  0, 32, "ChaCha20");
 	   
 	   this.key = aux;
		this.nonce=getNonce(key);
		this.iv = new IvParameterSpec(this.nonce);


	}

	public byte[] encrypt(String text) throws Exception {

        Cipher cipher = Cipher.getInstance("ChaCha20-Poly1305");

        cipher.init(Cipher.ENCRYPT_MODE, this.key, this.iv);

        byte[] encryptedText = cipher.doFinal(text.getBytes());

        return encryptedText;
    }

    public String decrypt(byte[] cText) throws Exception {

        Cipher cipher = Cipher.getInstance("ChaCha20-Poly1305");


        cipher.init(Cipher.DECRYPT_MODE, this.key,this.iv);

        byte[] decryptedText = cipher.doFinal(cText);

        return new String (decryptedText);

    }

    // A 256-bit secret key (32 bytes)
    private static SecretKey getKey() throws NoSuchAlgorithmException {
        KeyGenerator keyGen = KeyGenerator.getInstance("ChaCha20");
        keyGen.init(256, SecureRandom.getInstanceStrong());
        return keyGen.generateKey();
    }

    // 96-bit nonce (12 bytes)
    private static byte[] getNonce(String key) {
        byte[] newNonce = new byte[12];
        new SecureRandom().nextBytes(newNonce);
        Random r = new Random();
        r.setSeed(key.hashCode());
        r.nextBytes(newNonce);
        return newNonce;
    }
}
