package com.example.dchat.service.impl;

import com.example.dchat.dto.BlockDto;
import com.example.dchat.dto.PeerListDto;
import com.example.dchat.dto.PingDto;
import com.example.dchat.dto.TransactionDto;
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

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class BlockchainFacadeImpl implements BlockchainFacade {
    private final BlockchainService blockchainService;
    private final TransactionService transactionService;
    private final PeerService peerService;

    @Override
    public void handleBlock(BlockDto blockDto) {
        log.debug("received block: " + blockDto);
        Peer sender = new Peer(blockDto.getMeta().getSenderAddress());

        Block block = blockDto.getBlock();
        if (blockchainService.isValidBlock(block) && !blockchainService.isContains(block)) {
            blockchainService.addBlock(block);
            peerService.broadcastBlock(block);
        }
        peerService.addPeers(List.of(sender));
    }

    @Override
    public void handleTransaction(TransactionDto transactionDto) {
        log.debug("received transaction: " + transactionDto);
        Peer sender = new Peer(transactionDto.getMeta().getSenderAddress());

        Transaction transaction = transactionDto.getTransaction();
        if (transactionService.isValidTransaction(transaction) && !transactionService.isContains(transaction)) {
            transactionService.addTransaction(transaction);
            peerService.broadcastTransaction(transaction);
        }
        peerService.addPeers(List.of(sender));
    }

    @Override
    public void handlePing(PingDto pingDto) {
        log.debug("received ping: " + pingDto);
        Peer sender = new Peer(pingDto.getMeta().getSenderAddress());

        peerService.sharePeersWith(sender);

        int otherChainLength = pingDto.getChainHeight();
        List<Block> myChain = blockchainService.getChain();
        if (myChain.size() > otherChainLength) {
            for (int i = otherChainLength; i < myChain.size(); ++i) {
                peerService.sendBlock(sender, myChain.get(i));
            }
        }
        peerService.addPeers(List.of(sender));
    }

    @Override
    public void handlePeerList(PeerListDto peerListDto) {
        log.debug("received list of peers: " + peerListDto);
        Peer sender = new Peer(peerListDto.getMeta().getSenderAddress());

        List<Peer> peers = peerListDto.getPeerList().stream().map(Peer::new).collect(Collectors.toList());
        int chainLength = blockchainService.getChain().size();
        for (Peer peer : peers) {
            if (!peerService.isContains(peer))
                peerService.sendPing(peer, chainLength);
        }
        peerService.addPeers(peers);
        peerService.addPeers(List.of(sender));
    }
}
