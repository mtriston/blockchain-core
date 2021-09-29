package com.example.dchat.controller;

import com.example.dchat.dto.BlockDto;
import com.example.dchat.dto.PeerListDto;
import com.example.dchat.dto.PingDto;
import com.example.dchat.dto.TransactionDto;
import com.example.dchat.model.Block;
import com.example.dchat.model.Peer;
import com.example.dchat.model.Transaction;
import com.example.dchat.service.BlockchainService;
import com.example.dchat.service.PeerService;
import com.example.dchat.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BlockchainController {
    private final BlockchainService blockchainService;
    private final TransactionService transactionService;
    private final PeerService peerService;

    @PostMapping("/transaction")
    public void postTransaction(@RequestBody TransactionDto transactionDto) {
        Peer sender = new Peer(transactionDto.getMeta().getSenderAddress());

        Transaction transaction = transactionDto.getTransaction();
        if (transactionService.isValidTransaction(transaction) && !transactionService.isContains(transaction)) {
            transactionService.addTransaction(transaction);
            peerService.broadcastTransaction(transaction);
        }
        peerService.addPeers(List.of(sender));
    }

    @PostMapping("/block")
    public void postBlock(@RequestBody BlockDto blockDto) {
        Peer sender = new Peer(blockDto.getMeta().getSenderAddress());

        Block block = blockDto.getBlock();
        if (blockchainService.isValidBlock(block) && !blockchainService.isContains(block)) {
            blockchainService.addBlock(block);
            peerService.broadcastBlock(block);
        }
        peerService.addPeers(List.of(sender));
    }

    @PostMapping("/peer")
    public void postPeerList(@RequestBody PeerListDto peerList) {
        Peer sender = new Peer(peerList.getMeta().getSenderAddress());

        List<Peer> peers = peerList.getPeerList();
        int chainLength = blockchainService.getChain().size();
        for (Peer peer : peers) {
            if (!peerService.isContains(peer))
                peerService.sendPing(peer, chainLength);
        }
        peerService.addPeers(peers);
        peerService.addPeers(List.of(sender));
    }

    @PostMapping("/ping")
    public void postPing(@RequestBody PingDto pingDto) {
        Peer sender = new Peer(pingDto.getMeta().getSenderAddress());

        peerService.shareContactsWith(sender);

        int otherChainLength = pingDto.getChainHeight();
        List<Block> myChain = blockchainService.getChain();
        if (myChain.size() > otherChainLength) {
            for (int i = otherChainLength; i < myChain.size(); ++i) {
                peerService.sendBlock(sender, myChain.get(i));
            }
        }
        peerService.addPeers(List.of(sender));
    }
}
