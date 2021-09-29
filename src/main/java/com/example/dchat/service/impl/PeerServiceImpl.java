package com.example.dchat.service.impl;

import com.example.dchat.dto.*;
import com.example.dchat.model.Block;
import com.example.dchat.model.Peer;
import com.example.dchat.model.Transaction;
import com.example.dchat.repository.PeerRepository;
import com.example.dchat.service.PeerService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Log4j2
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
        log.info("Your node address - " + externalIp + ":" + port);
    }

    @Override
    public void addPeers(List<Peer> peers) {
        log.debug("Added/updated peers: " + peers);
        peerRepository.savePeers(peers
                .stream()
                .peek(peer -> peer.setLastSeen(new Date(System.currentTimeMillis())))
                .collect(Collectors.toList())
        );
    }

    @Override
    public boolean isContains(Peer peer) {
        return peerRepository.isContains(peer);
    }

    @Override
    public void broadcastBlock(Block block) {
        // Mb first we send block hash and then -> block (not sure) -> to minimize traffic
        List<Peer> peers = peerRepository.getActivityPeers();
        for (Peer peer : peers) {
            sendBlock(peer, block);
        }
    }

    @Override
    public void sendBlock(Peer peer, Block block) {
        log.debug(String.format("Send block %s\nTo peer %s", block, peer));
        webClient
                .post()
                .uri(peer.toString() + "/block")
                .body(Mono.just(new BlockDto(getMeta(), block)), BlockDto.class)
                .retrieve();
    }

    @Override
    public void broadcastTransaction(Transaction transaction) { // 1. Mb with transaction service?
        // in this method we should first send transaction hash (to minimize traffic)
        // If the recipient doesn't have this transaction -> then we send it to him
        List<Peer> peers = peerRepository.getActivityPeers();
        for (Peer peer : peers) {
            sendTransaction(peer, transaction);
        }
    }

    @Override
    public void sendTransaction(Peer peer, Transaction transaction) {
        log.debug(String.format("Send transaction %s\nTo peer %s", transaction, peer));
        webClient
                .post()
                .uri(peer.toString(), "/transaction")
                .body(Mono.just(new TransactionDto(getMeta(), transaction)), TransactionDto.class)
                .retrieve();
    }

    @Override
    public void shareContactsWith(Peer recipient) {
        log.debug("Share contacts with peer " + recipient);
        List<Peer> peers = peerRepository.getActivityPeers();
        webClient
                .post()
                .uri("http://"+ recipient.toString() + "/peer")
                .body(Mono.just(new PeerListDto(getMeta(), peers)), PeerListDto.class)
                .retrieve();
        }

    @Override
    public void sendPing(Peer peer, int chainLength) {
        log.debug("Send ping to peer " + peer);
            webClient
                    .post()
                    .uri("http://"+ peer.toString() + "/ping")
                    .body(Mono.just(new PingDto(getMeta(), chainLength)), PingDto.class)
                    .retrieve();
    }

    private MetaDto getMeta() {
        return new MetaDto(externalIp + ":" + port);
    }

    private String getExternalIp() {
        return Objects.requireNonNull(webClient
                .get()
                .uri("http://checkip.amazonaws.com")
                .retrieve()
                .bodyToMono(String.class).block()).strip();
    }
}
