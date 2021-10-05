package com.example.blockchain.service;

import com.example.blockchain.dto.*;
import com.example.blockchain.model.Transaction;

public interface BlockchainFacade {
    ChainDto getChain();
    void handleBlock(BlockDto blockDto);
    void addTransaction(Transaction transaction);
    void handleTransaction(TransactionDto transactionDto);
    void handlePing(PingDto pingDto);
    void handlePeerList(PeerListDto peerListDto);
    void resumeMining();
    void pauseMining();
}
