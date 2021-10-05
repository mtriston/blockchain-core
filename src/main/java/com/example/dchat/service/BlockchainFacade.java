package com.example.dchat.service;

import com.example.dchat.dto.*;

public interface BlockchainFacade {
    ChainDto getChain();
    void handleBlock(BlockDto blockDto);
    void handleTransaction(TransactionDto transactionDto);
    void handlePing(PingDto pingDto);
    void handlePeerList(PeerListDto peerListDto);
    void resumeMining();
    void pauseMining();
}
