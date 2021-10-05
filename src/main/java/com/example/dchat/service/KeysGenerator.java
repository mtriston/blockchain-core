package com.example.dchat.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.*;


public class KeysGenerator {

    public static final String SSH_DIR = System.getProperty("user.home") + "/.ssh/secrets/";
    private final KeyPairGenerator keyGen;
    private PrivateKey privateKey;
    private PublicKey publicKey;

    public KeysGenerator(int keyLength) throws NoSuchAlgorithmException {
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
            writeToFile(SSH_DIR + "public.key", getPublicKey().getEncoded(), false);
            writeToFile(SSH_DIR + "private.key", getPrivateKey().getEncoded(), true);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public void updateKeyPair() {
        File sshDir = new File(SSH_DIR);

        for (File f : sshDir.listFiles()) {
            f.delete();
        }
        try {
            createKeys();
            writeToFile(SSH_DIR + "public.key", getPublicKey().getEncoded(), false);
            writeToFile(SSH_DIR + "private.key", getPrivateKey().getEncoded(), true);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
