package com.example.dchat.repository;

import com.example.dchat.model.Block;

import java.util.List;

public interface ChainRepository {

    void saveBlock(Block block);
    void replaceChain(List<Block> chain);
    Block getLastBlock();
    List<Block> getChain();
    int getChainHeight(); // 1. why not length 2. We already have the last block number
                            // So when we receive new block -> we can see it's number
                            // And sender should not send the length of his chain together with new mined block
    boolean isContains(Block block);
}
