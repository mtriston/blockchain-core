package com.example.dchat.repository;

import com.example.dchat.model.Block;

import java.util.List;

public interface ChainRepository {

    void saveBlock(Block block);
    void replaceChain(List<Block> chain);
    Block getLastBlock();
    List<Block> getChain();
    boolean isContains(Block block);
}
