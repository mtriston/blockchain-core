package com.example.dchat.service;

import com.example.dchat.model.Block;
import com.example.dchat.model.Peer;
import com.example.dchat.model.Transaction;

import java.util.List;

public interface PeerService {
    void addPeers(List<Peer> peers);
    void broadcastBlock(Block block);
    void sendBlock(Peer peer, Block block);
    void broadcastTransaction(Transaction transaction);
    void sendTransaction(Peer peer, Transaction transaction);
    void sendPing(Peer peer, int chainLength);
    void shareContactsWith(Peer recipient);
}
