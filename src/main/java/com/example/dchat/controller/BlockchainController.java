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
        //TODO: обновить last seen отправителю
        Transaction transaction = transactionDto.getTransaction();
        if (transactionService.isValidTransaction(transaction) && transactionService.isUniqueTransaction(transaction)) {
            transactionService.addTransaction(transaction);
            //TODO: разослать транзакцию другим
        }
    }

    @PostMapping("/block")
    public void postBlock(@RequestBody BlockDto blockDto) {
        //TODO: обновить last seen отправителю
        Block block = blockDto.getBlock();
        if (blockchainService.isValidBlock(block) && blockchainService.isContains(block)) {
            blockchainService.addBlock(block);
            //TODO: разослать блок другим
        }
    }

    @PostMapping("/peer")
    public void postPeerList(@RequestBody PeerListDto peerList) {
        //TODO: обновить last seen отправителю
        List<Peer> peers = peerList.getPeerList();
        for (Peer peer : peers) {
            //TODO: пингануть всех
        }
        peerService.addPeers(peers);
    }

    @PostMapping("/ping")
    public void postPing(@RequestBody PingDto pingDto) {
        //TODO: отправить свои контакты отправителю пинга
        int otherChainLength = pingDto.getChainHeight();
        List<Block> myChain = blockchainService.getChain();
        if (myChain.size() > otherChainLength) {
            for (int i = otherChainLength; i < myChain.size(); ++i) {
                //TODO: отправить блок отправителю пинга
            }
        }
    }
}
