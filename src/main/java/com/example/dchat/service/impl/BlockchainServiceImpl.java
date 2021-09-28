package com.example.dchat.service.impl;

import com.example.dchat.model.Block;
import com.example.dchat.model.Transaction;
import com.example.dchat.repository.ChainRepository;
import com.example.dchat.service.BlockchainService;
import com.example.dchat.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BlockchainServiceImpl implements BlockchainService {

    private final static String HASH_PREFIX = "000";

    private final ChainRepository chainRepository;
    private final TransactionService transactionService;

    private Block createBlock() {
        List<Transaction> transactions = transactionService.getTransactions(20);
        Block lastBlock = chainRepository.getLastBlock();
        return new Block(lastBlock.getIndex() + 1, transactions, lastBlock.getHash());
    }

    private Block addBlock(Block block) {
        transactionService.removeTransactions(block.getTransactions());
        chainRepository.saveBlock(block);
        return block;
    }

    @Override
    public List<Block> getChain() {
        return chainRepository.getChain();
    }

    @Override
    public Block mineBlock() {
        Block block;
        do {
            block = createBlock();
        } while (!block.getHash().startsWith(HASH_PREFIX));
        return addBlock(block);
    }
}
