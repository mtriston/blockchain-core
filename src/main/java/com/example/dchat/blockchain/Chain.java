package com.example.dchat.blockchain;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class Chain {
    @Getter
    private final List<Block> blockchain;

    public Chain() {
        blockchain = new ArrayList<>();
        blockchain.add(new Block(0, null, null));
    }

    public void addBlock(List<Transaction> transactions) {
        blockchain.add(new Block(blockchain.size(), transactions, getLastBlock().getHash()));
    }

    public Block getLastBlock() {
        return blockchain.get(blockchain.size() - 1);
    }
}