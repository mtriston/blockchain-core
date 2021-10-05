package com.example.dchat.service.impl;

import com.example.dchat.dto.*;
import com.example.dchat.model.Block;
import com.example.dchat.model.Peer;
import com.example.dchat.model.Transaction;
import com.example.dchat.service.BlockchainFacade;
import com.example.dchat.service.BlockchainService;
import com.example.dchat.service.PeerService;
import com.example.dchat.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class BlockchainFacadeImpl implements BlockchainFacade {
    private final BlockchainService blockchainService;
    private final TransactionService transactionService;
    private final PeerService peerService;
    private Thread miningThread;

    @PostConstruct
    private void init() {
        miningThread = new Thread(() -> {
            while (true) {
                Block block = blockchainService.mineBlock();
                peerService.broadcastBlock(block);
            }
        });
        miningThread.start();
        log.info("Mining launched");
    }

    @Override
    public ChainDto getChain() {
        List<Block> chain = blockchainService.getChain();
        return new ChainDto(peerService.getMeta(), chain) ;
    }

    @Override
    public synchronized void handleBlock(BlockDto blockDto) {
        log.debug("received block: " + blockDto);
        peerService.addPeers(List.of(new Peer(blockDto.getMeta().getSenderAddress())));

        Block block = blockDto.getBlock();
        if (blockchainService.isValidBlock(block) && !blockchainService.isContains(block)) {
            blockchainService.addBlock(block);
            peerService.broadcastBlock(block);
        } else {
            resolveConflict();
        }
    }

    @Override
    public synchronized void handleTransaction(TransactionDto transactionDto) {
        log.debug("received transaction: " + transactionDto);
        peerService.addPeers(List.of(new Peer(transactionDto.getMeta().getSenderAddress())));

        Transaction transaction = transactionDto.getTransaction();
        if (transactionService.isValidTransaction(transaction) && !transactionService.isContains(transaction)) {
            transactionService.addTransaction(transaction);
            peerService.broadcastTransaction(transaction);
        }
    }

    @Override
    public synchronized void handlePing(PingDto pingDto) {
        log.debug("received ping: " + pingDto);
        Peer sender = new Peer(pingDto.getMeta().getSenderAddress());
        peerService.addPeers(List.of(sender));

        peerService.sharePeersWith(sender);

        int otherChainLength = pingDto.getChainHeight();
        List<Block> myChain = blockchainService.getChain();
        if (myChain.size() > otherChainLength) {
            for (int i = otherChainLength; i < myChain.size(); ++i) {
                peerService.sendBlock(sender, myChain.get(i));
            }
        }
    }

    @Override
    public synchronized void handlePeerList(PeerListDto peerListDto) {
        log.debug("received list of peers: " + peerListDto);
        Peer sender = new Peer(peerListDto.getMeta().getSenderAddress());
        peerService.addPeers(List.of(sender));

        List<Peer> peers = peerListDto.getPeerList().stream().map(Peer::new).collect(Collectors.toList());
        int chainLength = blockchainService.getChain().size();
        for (Peer peer : peers) {
            if (!peerService.isContains(peer))
                peerService.sendPing(peer, chainLength);
        }
        peerService.addPeers(peers);
    }

    @Override
    public synchronized void resumeMining() {
        log.info("Mining resumed");
        miningThread.notify();
    }

    @Override
    public synchronized void pauseMining() {
        log.info("Mining paused");
        try {
            miningThread.wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void resolveConflict() {
        int maxLength = blockchainService.getChain().size();
        List<Block> newChain = null;
        List<Peer> peers = peerService.getActivePeers();
        for (Peer peer : peers) {
            List<Block> otherChain = peerService.getChainFromPeer(peer).getChain();
            if (maxLength < otherChain.size() && blockchainService.isValidChain(otherChain)) {
                maxLength =otherChain.size();
                newChain = otherChain;
            }
        }
        if (newChain != null) {
            blockchainService.setChain(newChain);
            log.debug("Conflict resolved");
        }
    }
}
