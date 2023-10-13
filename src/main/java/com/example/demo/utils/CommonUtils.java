package com.example.demo.utils;

import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.Base64;
import java.util.HashMap;

@Component
public class CommonUtils {

    public String md5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes(StandardCharsets.UTF_8));
            BigInteger number = new BigInteger(1, messageDigest);
            String hashtext = number.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        }
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

//    public PublicKey getPublicKey(KeyPair keyPair) {
//        return keyPair.getPublic();
//    }
//
//    public PrivateKey getPrivateKey(KeyPair keyPair) {
//        return keyPair.getPrivate();
//    }
//
//    public String encrypted(PublicKey publicKey, String md5password) throws Exception{
//        Cipher encryptCipher = Cipher.getInstance("RSA");
//        encryptCipher.init(Cipher.ENCRYPT_MODE, publicKey);
//        byte[] encryptedBytes = encryptCipher.doFinal(md5password.getBytes());
//        String encryptedString = Base64.getEncoder().encodeToString(encryptedBytes);
//        return encryptedString;
//    }
//
//    public String decrypted(PrivateKey privateKey, String encryptedString) throws Exception{
//        Cipher decryptCipher = Cipher.getInstance("RSA");
//        decryptCipher.init(Cipher.DECRYPT_MODE, privateKey);
//        byte[] decryptedBytes = decryptCipher.doFinal(Base64.getDecoder().decode(encryptedString));
//        String decryptedString = new String(decryptedBytes);
//        return decryptedString;
//    }
//
//    public KeyPair getKeyPair() throws Exception {
//        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
//        keyPairGenerator.initialize(2048);
//        KeyPair keyPair = keyPairGenerator.generateKeyPair();
//        return keyPair;
//    }
}
