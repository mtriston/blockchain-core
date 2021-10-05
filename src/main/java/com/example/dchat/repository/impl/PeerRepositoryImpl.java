package com.example.dchat.repository.impl;


import com.example.dchat.model.Peer;
import com.example.dchat.repository.PeerRepository;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public class PeerRepositoryImpl implements PeerRepository {
    private final Set<Peer> peers = Collections.synchronizedSet(new HashSet<>());

    @Override
    public void savePeers(List<Peer> newPeers) {
        newPeers.forEach(peers::remove);
        peers.addAll(newPeers);
    }

    @Override
    public void removePeer(Peer peer) {
        peers.remove(peer);
    }

    @Override
    public List<Peer> getPeers() {
        return new ArrayList<>(peers);
    }

    @Override
    public boolean isContains(Peer peer) {
        return peers.contains(peer);
    }
}
