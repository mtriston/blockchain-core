package com.example.blockchain.repository;

import com.example.blockchain.model.Block;

import java.util.List;

public interface ChainRepository {

    void saveBlock(Block block);
    void setChain(List<Block> chain);
    Block getLastBlock();
    List<Block> getChain();
    boolean isContains(Block block);
}
