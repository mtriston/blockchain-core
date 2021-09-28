package com.example.dchat.repository;

import com.example.dchat.model.Peer;

import java.util.List;

public interface PeerRepository {
    void savePeers(List<Peer> newPeers);

    void removePeer(Peer peer);

    List<Peer> getActivityPeers();
}
