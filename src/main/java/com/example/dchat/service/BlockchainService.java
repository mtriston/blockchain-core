package com.example.dchat.service;

import com.example.dchat.blockchain.Block;
import com.example.dchat.blockchain.Chain;
import com.example.dchat.blockchain.Transaction;
import com.example.dchat.dto.MineResponse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
public class BlockchainService {

    private final Chain chain = new Chain();
    private final List<Transaction> pendingTransactions = Collections.synchronizedList(new ArrayList<>());
    private final UUID nodeIdentifier = UUID.randomUUID();

    private Block addNewBlock() {
        List <Transaction> currentTransactions = new ArrayList<>(pendingTransactions);
        chain.addBlock(currentTransactions);
        pendingTransactions.removeAll(currentTransactions);
        return chain.getLastBlock();
    }

    public void addNewTransaction(Transaction transaction) {
        pendingTransactions.add(transaction);
    }

    public MineResponse mineLastBlock() {
        // Скорее всего не нужно майнить, если у последнего блока proof > 0
        Block lastBlock = chain.getLastBlock();
        lastBlock.mineBlock();

        addNewTransaction(Transaction.builder()
                .sender("System")
                .recipient(nodeIdentifier.toString())
                .message("Reward for finding the proof")
                .amount(10)
                .build()
        );

        Block newBlock = addNewBlock();
        return MineResponse.builder()
                .message("New Block Created")
                .index(newBlock.getIndex())
                .transactions(newBlock.getTransactions())
                .proof(lastBlock.getNonce())
                .previousHash(newBlock.getPreviousHash())
                .build();
    }

    public List<Block> getChain() {
        return chain.getBlockchain();
    }
}