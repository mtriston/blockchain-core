package com.example.dchat.service;

import com.example.dchat.model.Block;

import java.util.List;

public interface BlockchainService {
    List<Block> getChain();
    Block mineBlock();
}
