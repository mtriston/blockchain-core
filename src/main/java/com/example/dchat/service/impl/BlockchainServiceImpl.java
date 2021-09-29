package com.example.dchat.service.impl;

import com.example.dchat.model.Block;
import com.example.dchat.model.Transaction;
import com.example.dchat.repository.ChainRepository;
import com.example.dchat.service.BlockchainService;
import com.example.dchat.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@Service
@RequiredArgsConstructor
public class BlockchainServiceImpl implements BlockchainService {

    private final static String HASH_PREFIX = "000";

    private final ChainRepository chainRepository;
    private final TransactionService transactionService;

    private Block createBlock() {
        List<Transaction> transactions = transactionService.getTransactions();
        transactions = transactions.subList(0, Math.min(transactions.size(), 20)); // with max fee, else -> random

        transactions.add(transactionService.createRewardTransaction());

        Block lastBlock = chainRepository.getLastBlock();
        return new Block(lastBlock.getIndex() + 1, transactions, lastBlock.getHash());
    }

    @Override
    public void addBlock(Block block) {
        log.debug("added new block to chain: " + block);
        transactionService.removeTransactions(block.getTransactions());
        chainRepository.saveBlock(block);
    }

    @Override
    public List<Block> getChain() {
        return chainRepository.getChain();
    }

    @Override
    public boolean isValidBlock(Block block) {
        return Block.calculateBlockHash(block).equals(block.getHash()) &&
                block.getHash().startsWith(HASH_PREFIX) &&
                    chainRepository.getLastBlock().getHash().equals(block.getPreviousHash()) &&
                        block.getTransactions().size() > 1 &&
                            transactionService.isValidTransactionsFromBlock(block.getTransactions());
    }

    @Override
    public boolean isContains(Block block) {
        return chainRepository.isContains(block);
    }

    @Override
    public void mineBlock() {
        //TODO: При отсутствии транзакций, цикл будет повторяться в холостую. Можно сделать лучше (например Producer\Consumer)
        Block block;
        do {
            block = createBlock();
        } while (!isValidBlock(block));
        log.debug("Created new block: " + block);
        addBlock(block);
    }
}
