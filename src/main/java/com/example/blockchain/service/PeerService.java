package com.example.blockchain.service;

import com.example.blockchain.dto.ChainDto;
import com.example.blockchain.dto.MetaDto;
import com.example.blockchain.model.Block;
import com.example.blockchain.model.Peer;
import com.example.blockchain.model.Transaction;

import java.util.List;

public interface PeerService {
    void addPeers(List<Peer> peers);
    boolean isContains(Peer peer);
    void broadcastBlock(Block block);
    void sendBlock(Peer peer, Block block);
    void broadcastTransaction(Transaction transaction);
    void sendTransaction(Peer peer, Transaction transaction);
    void sendPing(Peer peer, int chainLength);
    void sharePeersWith(Peer recipient);
    List<Peer> getActivePeers();
    ChainDto getChainFromPeer(Peer peer);
    MetaDto getMeta();
}
