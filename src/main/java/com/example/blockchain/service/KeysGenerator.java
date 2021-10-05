package com.example.blockchain.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.*;

@Log4j2
@Service
public class KeysGenerator {

    @Value("${cryptography.key.path}")
    private String KEY_PATH;
    @Value("${cryptography.key.public.file}")
    private String PUBLIC_KEY_FILE;
    @Value("${cryptography.key.private.file}")
    private String PRIVATE_KEY_FILE;
    private static final int KEY_LENGTH = 2048;
    private final KeyPairGenerator keyGen;
    private PrivateKey privateKey;
    private PublicKey publicKey;

    public KeysGenerator() throws NoSuchAlgorithmException {
        this.keyGen = KeyPairGenerator.getInstance("RSA");
        this.keyGen.initialize(KEY_LENGTH);
    }

    private void createKeys() {
        KeyPair pair = this.keyGen.generateKeyPair();
        this.privateKey = pair.getPrivate();
        this.publicKey = pair.getPublic();
    }

    public PrivateKey getPrivateKey() {
        return this.privateKey;
    }

    public PublicKey getPublicKey() {
        return this.publicKey;
    }

    public void writeToFile(String path, byte[] key, boolean isPrivate) throws IOException {
        File f = new File(path);
        f.getParentFile().mkdirs();

        try (FileOutputStream fos = new FileOutputStream(f)) {
            fos.write(key);
            fos.flush();
        }
        f.setExecutable(false);
        f.setWritable(false);
        if (isPrivate) {
            f.setReadable(true, true);
        }
    }

    public void generateKeyPair() {
        try {
            createKeys();
            writeToFile(KEY_PATH + PUBLIC_KEY_FILE, getPublicKey().getEncoded(), false);
            writeToFile(KEY_PATH + PRIVATE_KEY_FILE, getPrivateKey().getEncoded(), true);
            log.info("RSA keys generated");
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public void updateKeyPair() {
        File sshDir = new File(KEY_PATH);

        for (File f : sshDir.listFiles()) {
            f.delete();
        }
        try {
            createKeys();
            writeToFile(KEY_PATH + PUBLIC_KEY_FILE, getPublicKey().getEncoded(), false);
            writeToFile(KEY_PATH + PRIVATE_KEY_FILE, getPrivateKey().getEncoded(), true);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
