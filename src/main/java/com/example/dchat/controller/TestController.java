package com.example.dchat.controller;

import com.example.dchat.model.Block;
import com.example.dchat.model.Peer;
import com.example.dchat.model.Transaction;
import com.example.dchat.repository.PeerRepository;
import com.example.dchat.repository.TransactionRepository;
import com.example.dchat.service.BlockchainService;
import com.example.dchat.service.PeerService;
import com.example.dchat.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

//TODO: удалить контроллер. Нужен только для отладки
@RestController
@RequiredArgsConstructor
public class TestController {
    private final BlockchainService blockchainService;
    private final TransactionService transactionService;
    private final TransactionRepository transactionRepository;
    private final PeerService peerService;
    private final PeerRepository peerRepository;

    @GetMapping("/transaction") // transactions?
    List<Transaction> getTransactions() {
        return new ArrayList<>(transactionRepository.getTransactions());
    }

    @GetMapping("/chain")
    List<Block> getChain() {
        return new ArrayList<>(blockchainService.getChain());
    }

    @GetMapping("/peer") // peers?
    List<Peer> getPeer() {
        return new ArrayList<>(peerRepository.getActivePeers());
    }
}
