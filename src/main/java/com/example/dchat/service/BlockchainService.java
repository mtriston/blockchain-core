package com.example.dchat.service;

import com.example.dchat.model.Block;
import com.example.dchat.model.Transaction;
import com.example.dchat.model.response.MineResponse;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class BlockchainService {

    @Getter
    private final List<Block> chain = new ArrayList<>();
    private final List<Transaction> transactions = new ArrayList<>();
    private final UUID nodeIdentifier = UUID.randomUUID();

    public BlockchainService() {
        // Create a genesis (first) block
        chain.add(new Block(0, transactions, "1"));
    }

    private Block addNewBlock() {
        chain.add(new Block(chain.size(), transactions, getLastBlock().getHash()));
        transactions.clear();
        return chain.get(chain.size() - 1);
    }

    public void addNewTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

    public Block getLastBlock() {
        return chain.get(chain.size() - 1);
    }

    public MineResponse mineLastBlock() {
        // Скорее всего не нужно майнить, если у последнего блока proof > 0
        Block lastBlock = getLastBlock();
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
                .proof(lastBlock.getProof())
                .previousHash(newBlock.getPreviousHash())
                .build();
    }

//    private boolean isValidChain(List<Block> ch) {
//        for (int i = 1; i < ch.size(); ++i) {
//            if (ch.get(i - 1).getHash().equals(ch.get(i).getPreviousHash())) {
//                return false;
//            }
//        }
//        return true;
//    }
}