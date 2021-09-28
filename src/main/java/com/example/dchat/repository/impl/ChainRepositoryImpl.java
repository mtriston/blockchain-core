package com.example.dchat.repository.impl;

import com.example.dchat.model.Block;
import com.example.dchat.repository.ChainRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Repository
public class ChainRepositoryImpl implements ChainRepository {

    private final List<Block> blockchain = Collections.synchronizedList(new ArrayList<>());

    ChainRepositoryImpl() {
        blockchain.add(new Block(0, null, null));
    }

    @Override
    public void saveBlock(Block block) {
        blockchain.add(block);
    }

    @Override
    public void replaceChain(List<Block> chain) {
        blockchain.clear();
        blockchain.addAll(chain);
    }

    @Override
    public Block getLastBlock() {
        return blockchain.get(blockchain.size() - 1);
    }

    @Override
    public List<Block> getChain() {
        return blockchain;
    }

    @Override
    public int getChainHeight() {
        return blockchain.size();
    }

    @Override
    public boolean isContains(Block block) {
        return blockchain.contains(block);
    }
}
