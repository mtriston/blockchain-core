package com.example.blockchain.service.impl;

import com.example.blockchain.repository.ChainRepository;
import com.example.blockchain.model.Block;
import com.example.blockchain.model.Transaction;
import com.example.blockchain.service.BlockchainService;
import com.example.blockchain.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Log4j2
@Service
@RequiredArgsConstructor
public class BlockchainServiceImpl implements BlockchainService {

    @Value("${mining.hash.prefix}")
    private String HASH_PREFIX;
    @Value("${mining.transaction.pool.size}")
    private int TRANSACTION_POOL_SIZE;

    private final ChainRepository chainRepository;
    private final TransactionService transactionService;

    private Block createBlock() {
        List<Transaction> transactions = new ArrayList<>(transactionService.getTransactions());
        transactions = transactions.subList(0, Math.min(transactions.size(), TRANSACTION_POOL_SIZE)); // with max fee, else -> random

        transactions.add(0, transactionService.createRewardTransaction());

        Block lastBlock = chainRepository.getLastBlock();
        return new Block(lastBlock.getIndex() + 1, transactions, lastBlock.getHash());
    }

    @Override
    public void addBlock(Block block) {
        log.debug("Added new block to chain: " + block);
        transactionService.removeTransactions(block.getTransactions());
        chainRepository.saveBlock(block);
    }

    @Override
    public List<Block> getChain() {
        return chainRepository.getChain();
    }

    @Override
    public void setChain(List<Block> chain) {
        chainRepository.setChain(chain);
    }

    @Override
    public boolean isValidBlock(Block block) {
        return Block.calculateBlockHash(block).equals(block.getHash()) &&
                block.getHash().startsWith(HASH_PREFIX) &&
                chainRepository.getLastBlock().getHash().equals(block.getPreviousHash()) &&
                transactionService.isValidTransactionsFromBlock(block.getTransactions());
    }

    @Override
    public boolean isValidChain(List<Block> chain) {
        //todo: реализовать
        return false;
    }

    @Override
    public boolean isContains(Block block) {
        return chainRepository.isContains(block);
    }

    @Override
    public Block mineBlock() {
        //TODO: При отсутствии транзакций, цикл будет повторяться в холостую. Можно сделать лучше (например Producer\Consumer)
        Block block;
        do {
            block = createBlock();
        } while (!isValidBlock(block));
        log.debug("Created new block: " + block);
        addBlock(block);
        return block;
    }
}
