package com.example.blockchain.controller;

import com.example.blockchain.repository.TransactionRepository;
import com.example.blockchain.service.TransactionService;
import com.example.blockchain.repository.PeerRepository;
import com.example.blockchain.model.Peer;
import com.example.blockchain.model.Transaction;
import com.example.blockchain.service.BlockchainService;
import com.example.blockchain.service.PeerService;
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

    @GetMapping("/peer") // peers?
    List<Peer> getPeer() {
        return peerRepository.getPeers();
    }
}
