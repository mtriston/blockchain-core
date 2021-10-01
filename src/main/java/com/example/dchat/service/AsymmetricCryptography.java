package com.example.dchat.service;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;

import org.apache.tomcat.util.codec.binary.Base64;

public class AsymmetricCryptography {

    private static final String PATH_TO_PUBLIC_KEY = "secrets/publicKey";
    private static final String PATH_TO_PRIVATE_KEY = "secrets/privateKey";

    private Cipher cipher;
    private PrivateKey privateKey;
    private PublicKey publicKey;

    public AsymmetricCryptography()  {
        try {
            this.cipher = Cipher.getInstance("RSA");
            this.privateKey = getPrivate(PATH_TO_PRIVATE_KEY);
            this.publicKey = getPublic(PATH_TO_PUBLIC_KEY);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // https://docs.oracle.com/javase/8/docs/api/java/security/spec/PKCS8EncodedKeySpec.html
    public PrivateKey getPrivate(String filename) throws Exception {
        byte[] keyBytes = Files.readAllBytes(new File(filename).toPath());
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePrivate(spec);
    }

    // https://docs.oracle.com/javase/8/docs/api/java/security/spec/X509EncodedKeySpec.html
    public PublicKey getPublic(String filename) throws Exception {
        byte[] keyBytes = Files.readAllBytes(new File(filename).toPath());
        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePublic(spec);
    }

    public String encryptMsg(String msg) throws UnsupportedEncodingException, IllegalBlockSizeException,
            BadPaddingException, InvalidKeyException {

        this.cipher.init(Cipher.ENCRYPT_MODE, this.privateKey);
        return Base64.encodeBase64String(cipher.doFinal(msg.getBytes(StandardCharsets.UTF_8)));
    }

    public String decryptMsg(String msg, String key) throws InvalidKeyException, UnsupportedEncodingException,
            IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, InvalidKeySpecException {

        byte[] publicBytes = Base64.decodeBase64(key);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(keySpec);

        this.cipher.init(Cipher.DECRYPT_MODE, publicKey);
        return new String(cipher.doFinal(Base64.decodeBase64(msg)), StandardCharsets.UTF_8);
    }
}