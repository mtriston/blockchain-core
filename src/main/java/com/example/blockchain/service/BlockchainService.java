package com.example.blockchain.service;

import com.example.blockchain.model.Block;

import java.util.List;

public interface BlockchainService {
    void addBlock(Block block);
    List<Block> getChain();
    void setChain(List<Block> chain);
    boolean isValidBlock(Block block);
    boolean isValidChain(List<Block> chain);
    boolean isContains(Block block);
    Block mineBlock();
}
