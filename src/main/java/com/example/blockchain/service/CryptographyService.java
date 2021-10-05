package com.example.blockchain.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.security.*;
import java.security.spec.EncodedKeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

@Log4j2
@Service
@RequiredArgsConstructor
public class CryptographyService {
    @Value("${cryptography.key.path}" + "${cryptography.key.private.file}")
    private String PATH_TO_PRIVATE_KEY;
    @Value("${cryptography.key.path}" + "${cryptography.key.public.file}")
    private String PATH_TO_PUBLIC_KEY;

    private PrivateKey privateKey;
    private PublicKey publicKey;

    private final KeysGenerator keysGenerator;

    @PostConstruct
    void initKeys() {
        try {
            this.privateKey = getPrivate(PATH_TO_PRIVATE_KEY);
            this.publicKey = getPublic(PATH_TO_PUBLIC_KEY);
            log.info("RSA keys found");
        } catch (Exception e) {
            keysGenerator.generateKeyPair();
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

    private PublicKey stringToPublicKey(String msg) throws Exception {
        byte[] keyBytes = java.util.Base64.getDecoder().decode(msg);
        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(spec);
    }

    private PrivateKey stringToPrivateKey(String msg) throws Exception {
        byte[] keyBytes = java.util.Base64.getDecoder().decode(msg);
        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePrivate(spec);
    }

    private static String getStringKeyFromFile(String filepath) throws IOException {
        byte[] keyBytes = Files.readAllBytes(new File(filepath).toPath());
        return java.util.Base64.getEncoder().encodeToString(keyBytes);
    }

    public String encryptMsg(String msg, String recipientPublicKeyString) throws Exception {
        Cipher encryptCipher = Cipher.getInstance("RSA");
        PublicKey recipientPublicKey = stringToPublicKey(recipientPublicKeyString);
        encryptCipher.init(Cipher.ENCRYPT_MODE, recipientPublicKey);
        byte[] msgBytes = msg.getBytes(StandardCharsets.UTF_8);
        byte[] encryptedMsgBytes = encryptCipher.doFinal(msgBytes);
        return java.util.Base64.getEncoder().encodeToString(encryptedMsgBytes);
    }

    public String decryptMsg(String msg) throws InvalidKeyException, NoSuchAlgorithmException,
            NoSuchPaddingException {
        Cipher decryptCipher = Cipher.getInstance("RSA");
        decryptCipher.init(Cipher.DECRYPT_MODE, this.privateKey);
        byte[] encryptedMsgBytes = java.util.Base64.getDecoder().decode(msg);
        try {
            byte[] decryptedMsgBytes = decryptCipher.doFinal(encryptedMsgBytes);
            return new String(decryptedMsgBytes, StandardCharsets.UTF_8);
        }
        catch (IllegalBlockSizeException | BadPaddingException e) {
            log.info("Message decryption failed");
            return null;
            //todo: пробросить свое исключение
        }
    }

    public String signMsg(String msg) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException,
            IllegalBlockSizeException, BadPaddingException {
        Cipher encryptCipher = Cipher.getInstance("RSA");
        encryptCipher.init(Cipher.ENCRYPT_MODE, this.privateKey);
        byte[] msgBytes = msg.getBytes(StandardCharsets.UTF_8);
        byte[] signedMsgBytes = encryptCipher.doFinal(msgBytes);
        return java.util.Base64.getEncoder().encodeToString(signedMsgBytes);
    }

    public String verifyDigitalSignature(String msg, String senderPublicKeyString) throws Exception {
        Cipher decryptCipher = Cipher.getInstance("RSA");
        PublicKey senderPublicKey = stringToPublicKey(senderPublicKeyString);
        decryptCipher.init(Cipher.DECRYPT_MODE, senderPublicKey);
        byte[] encryptedMsgBytes = java.util.Base64.getDecoder().decode(msg);
        byte[] decryptedMsgBytes = decryptCipher.doFinal(encryptedMsgBytes);
        return new String(decryptedMsgBytes, StandardCharsets.UTF_8);
    }
}