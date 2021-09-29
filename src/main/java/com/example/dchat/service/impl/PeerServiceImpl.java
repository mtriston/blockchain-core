package com.example.dchat.service.impl;

import com.example.dchat.dto.*;
import com.example.dchat.model.Block;
import com.example.dchat.model.Peer;
import com.example.dchat.model.Transaction;
import com.example.dchat.repository.PeerRepository;
import com.example.dchat.service.PeerService;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class PeerServiceImpl implements PeerService {

    private final PeerRepository peerRepository;
    private final WebClient webClient;
    private final String externalIp;
    private final int port;

    public PeerServiceImpl(PeerRepository peerRepository, WebClient webClient) {
        this.peerRepository = peerRepository;
        this.webClient = webClient;
        this.externalIp = getExternalIp();
        this.port = 8080;
    }

    @Override
    public void addPeers(List<Peer> peers) {
        peerRepository.savePeers(peers);
    }

    @Override
    public boolean isContains(Peer peer) {
        return peerRepository.isContains(peer);
    }

    @Override
    public void broadcastBlock(Block block) {
        List<Peer> peers = peerRepository.getActivityPeers();
        for (Peer peer : peers) {
            sendBlock(peer, block);
        }
    }

    @Override
    public void sendBlock(Peer peer, Block block) {
        webClient
                .post()
                .uri(peer.toString() + "/block")
                .body(Mono.just(new BlockDto(getMeta(), block)), BlockDto.class)
                .retrieve();
    }

    @Override
    public void broadcastTransaction(Transaction transaction) {
        List<Peer> peers = peerRepository.getActivityPeers();
        for (Peer peer : peers) {
            sendTransaction(peer, transaction);
        }
    }

    @Override
    public void sendTransaction(Peer peer, Transaction transaction) {
        webClient
                .post()
                .uri(peer.toString(), "/transaction")
                .body(Mono.just(new TransactionDto(getMeta(), transaction)), TransactionDto.class)
                .retrieve();
    }

    @Override
    public void shareContactsWith(Peer recipient) {
        List<Peer> peers = peerRepository.getActivityPeers();
        webClient
                .post()
                .uri(recipient.toString(), "/peer")
                .body(Mono.just(new PeerListDto(getMeta(), peers)), PeerListDto.class)
                .retrieve();
        }

    @Override
    public void sendPing(Peer peer, int chainLength) {
            webClient
                    .post()
                    .uri(peer.toString(), "/ping")
                    .body(Mono.just(new PingDto(getMeta(), chainLength)), PingDto.class)
                    .retrieve();
    }

    private MetaDto getMeta() {
        return new MetaDto(externalIp + ":" + port);
    }

    private String getExternalIp() {
        return webClient
                .get()
                .uri("http://checkip.amazonaws.com")
                .retrieve()
                .bodyToMono(String.class).block();
    }
}
