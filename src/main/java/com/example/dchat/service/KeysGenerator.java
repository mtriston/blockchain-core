package com.example.dchat.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.*;

public class KeysGenerator {

    private final KeyPairGenerator keyGen;
    private PrivateKey privateKey;
    private PublicKey publicKey;

    public KeysGenerator(int keyLength) throws NoSuchAlgorithmException, NoSuchProviderException {
        this.keyGen = KeyPairGenerator.getInstance("RSA");
        this.keyGen.initialize(keyLength);
    }

    public void createKeys() {
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

    public void writeToFile(String path, byte[] key) throws IOException {
        File f = new File(path);
        f.getParentFile().mkdirs();

        FileOutputStream fos = new FileOutputStream(f);
        fos.write(key);
        fos.flush();
        fos.close();
    }

    public void generateKeyPair() {
        try {
            createKeys();
            writeToFile("secrets/publicKey", getPublicKey().getEncoded());
            writeToFile("secrets/privateKey", getPrivateKey().getEncoded());
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
