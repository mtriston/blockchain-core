package com.example.dchat.service;

import com.example.dchat.dto.BlockDto;
import com.example.dchat.dto.PeerListDto;
import com.example.dchat.dto.PingDto;
import com.example.dchat.dto.TransactionDto;

public interface BlockchainFacade {
    void handleBlock(BlockDto blockDto);
    void handleTransaction(TransactionDto transactionDto);
    void handlePing(PingDto pingDto);
    void handlePeerList(PeerListDto peerListDto);
    void resumeMining();
    void pauseMining();
}
