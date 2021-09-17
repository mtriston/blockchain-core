package com.example.dchat.model;

import lombok.Data;
import lombok.extern.java.Log;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

@Log
@Data
public class Block {
    private static final int MINE_PREFIX = 3;

    private int index;
    private String hash;
    private String previousHash;
    private List<Transaction> transactions;
    long timeStamp;
    private int proof;

    public Block(int index, List<Transaction> transactions, String previousHash) {
        this.index = index;
        this.transactions = new ArrayList<>(transactions);
        this.previousHash = previousHash;
        this.timeStamp = System.currentTimeMillis() / 1000;
        this.hash = calculateBlockHash();
    }

    private String calculateBlockHash() {
        String dataToHash = previousHash
                + index
                + timeStamp
                + proof
                + transactions;
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            log.log(Level.SEVERE, e.getMessage());
            System.exit(-1);
        }
        byte[] bytes = digest.digest(dataToHash.getBytes(StandardCharsets.UTF_8));
        StringBuilder buffer = new StringBuilder();
        for (byte b : bytes) {
            buffer.append(String.format("%02x", b));
        }
        return buffer.toString();
    }

    public void mineBlock() {
        String prefixString = new String(new char[MINE_PREFIX]).replace('\0', '0');
        while (!hash.substring(0, MINE_PREFIX).equals(prefixString)) {
            proof++;
            hash = calculateBlockHash();
        }
    }
}
