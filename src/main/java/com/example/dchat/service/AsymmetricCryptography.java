package com.example.dchat.service;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.security.*;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class AsymmetricCryptography {

    private static final String PATH_TO_PRIVATE_KEY = "secrets/private.key";
    private static final String PATH_TO_PUBLIC_KEY = "secrets/public.key";

    private PrivateKey privateKey;
    private PublicKey publicKey;

    public AsymmetricCryptography()  {
        try {
            this.privateKey = getPrivate(PATH_TO_PRIVATE_KEY);
            this.publicKey = getPublic(PATH_TO_PUBLIC_KEY);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public PrivateKey getPrivate(String filename) throws Exception {
        byte[] keyBytes = Files.readAllBytes(new File(filename).toPath());
        EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePrivate(spec);
    }

    public PublicKey getPublic(String filename) throws Exception {
        byte[] keyBytes = Files.readAllBytes(new File(filename).toPath());
        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(spec);
    }

    public PublicKey stringToPublicKey(String msg) throws Exception {
        byte[] keyBytes = java.util.Base64.getDecoder().decode(msg);
        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(spec);
    }

    public PrivateKey stringToPrivateKey(String msg) throws Exception {
        byte[] keyBytes = java.util.Base64.getDecoder().decode(msg);
        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePrivate(spec);
    }

    public static String getStringKeyFromFile(String filepath) throws IOException {
        byte[] keyBytes = Files.readAllBytes(new File(filepath).toPath());
        String recipientPublicKey = java.util.Base64.getEncoder().encodeToString(keyBytes);
        return recipientPublicKey;
    }

    public String encryptMsg(String msg, String recipientPublicKeyString) throws Exception {

        Cipher encryptCipher = Cipher.getInstance("RSA");

<<<<<<< HEAD
        PublicKey recipientPublicKey = stringToPublicKey(recipientPublicKeyString);

        encryptCipher.init(Cipher.ENCRYPT_MODE, recipientPublicKey);
        byte[] msgBytes = msg.getBytes(StandardCharsets.UTF_8);
        byte[] encryptedMsgBytes = encryptCipher.doFinal(msgBytes);
        String encryptedMsg = java.util.Base64.getEncoder().encodeToString(encryptedMsgBytes);
        return encryptedMsg;
=======
        this.cipher.init(Cipher.ENCRYPT_MODE, this.privateKey);
        return Base64.encodeBase64String(cipher.doFinal(msg.getBytes(StandardCharsets.UTF_8)));
>>>>>>> 508b31ae580151c394069bb51a04a6e208bf914a
    }

    public String decryptMsg(String msg, String key) throws InvalidKeyException, UnsupportedEncodingException,
            IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, InvalidKeySpecException,
            NoSuchPaddingException {

        Cipher decryptCipher = Cipher.getInstance("RSA");

        decryptCipher.init(Cipher.DECRYPT_MODE, this.privateKey);
        byte[] encryptedMsgBytes = java.util.Base64.getDecoder().decode(msg);
        byte[] decryptedMsgBytes = decryptCipher.doFinal(encryptedMsgBytes);
        String decryptedMsg = new String(decryptedMsgBytes, StandardCharsets.UTF_8);

<<<<<<< HEAD
        return decryptedMsg;
=======
        this.cipher.init(Cipher.DECRYPT_MODE, publicKey);
        return new String(cipher.doFinal(Base64.decodeBase64(msg)), StandardCharsets.UTF_8);
>>>>>>> 508b31ae580151c394069bb51a04a6e208bf914a
    }
}