package com.example.dchat.service;

import com.example.dchat.model.Block;

import java.util.List;

public interface BlockchainService {
    Block addBlock(Block block);
    List<Block> getChain();
    boolean isValidBlock(Block block);
    boolean isContains(Block block);
    Block mineBlock();
}
