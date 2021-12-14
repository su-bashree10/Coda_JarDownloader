package com.presidiojardownloader.hashing;

import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;  
public class EncryptionDecryptionAES {  
    static Cipher cipher;  
    public static SecretKey secretKey ;
    static {
    	try {
      	  KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(128); // block size is 128bits
            secretKey = keyGenerator.generateKey();
            cipher = Cipher.getInstance("AES");
      	}catch(Exception e) {
      		e.printStackTrace();
      	}
    }
//    public EncryptionDecryptionAES() {
//    	try {
//    	  KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
//          keyGenerator.init(128); // block size is 128bits
//          secretKey = keyGenerator.generateKey();
//          cipher = Cipher.getInstance("AES");
//    	}catch(Exception e) {
//    		e.printStackTrace();
//    	}
//	}	


    public static String encrypt(String plainText, SecretKey secretKey)
            throws Exception {
        byte[] plainTextByte = plainText.getBytes();
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedByte = cipher.doFinal(plainTextByte);
        Base64.Encoder encoder = Base64.getEncoder();
        String encryptedText = encoder.encodeToString(encryptedByte);
        return encryptedText;
    }

    public  static String decrypt(String encryptedText, SecretKey secretKey)
            throws Exception {
        Base64.Decoder decoder = Base64.getDecoder();
        byte[] encryptedTextByte = decoder.decode(encryptedText);
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decryptedByte = cipher.doFinal(encryptedTextByte);
        String decryptedText = new String(decryptedByte);
        return decryptedText;
    }
}