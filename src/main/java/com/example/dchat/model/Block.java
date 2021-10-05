package com.example.dchat.model;

import lombok.Value;
import lombok.extern.java.Log;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;

@Log
@Value
public class Block {
    int index;
    String previousHash;
    List<Transaction> transactions;
    long timeStamp;
    int nonce;
    // private String contentHash; // this is only content (transactions) hash - not sure we need it
    String hash; // this is block hash

    // Note: we need to know that block was mined by someone concrete (he adds coins to his wallet after mining)
    // Question: how would peer add the coin to his "account"?

    public Block(int index, List<Transaction> transactions, String previousHash) {
        this.index = index;
        this.previousHash = previousHash;
        this.transactions = transactions;
        this.timeStamp = System.currentTimeMillis() / 1000;
        this.nonce = new Random().nextInt();
        this.hash = calculateBlockHash(this);
    }

    public Block(int index, String previousHash, List<Transaction> transactions, long timeStamp, int nonce) {
        this.index = index;
        this.previousHash = previousHash;
        this.transactions = transactions;
        this.timeStamp = timeStamp;
        this.nonce = nonce;
        this.hash = calculateBlockHash(this);
    }

    public static String calculateBlockHash(Block block) {
        String dataToHash = block.previousHash
                + block.index
                + block.timeStamp
                + block.nonce
                + block.transactions;
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
}
