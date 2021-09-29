package com.example.dchat.repository;

import com.example.dchat.model.Peer;

import java.util.List;

public interface PeerRepository {

    void savePeers(List<Peer> newPeers);
    void removePeer(Peer peer); // or remove not active peers (for last 3 months for example)
    List<Peer> getActivityPeers();
    boolean isContains(Peer peer);

}
