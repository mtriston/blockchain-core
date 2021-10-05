package com.example.blockchain.repository;

import com.example.blockchain.model.Peer;

import java.util.List;

public interface PeerRepository {

    void savePeers(List<Peer> newPeers);
    void removePeer(Peer peer); // or remove not active peers (for last 3 months for example)
    List<Peer> getPeers();
    boolean isContains(Peer peer);

}
