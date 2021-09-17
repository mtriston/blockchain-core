package com.example.dchat.controller;

import com.example.dchat.model.Block;
import com.example.dchat.model.response.MineResponse;
import com.example.dchat.model.Transaction;
import com.example.dchat.service.BlockchainService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class BlockchainController {
    private final BlockchainService blockchainService;

    @GetMapping("/mine")
    public MineResponse mineBlock() {
        return blockchainService.mineLastBlock();
    }

    @PostMapping("/transactions/new")
    public String newTransaction(@RequestBody Transaction transaction) {
        blockchainService.addNewTransaction(transaction);
        return "Added a new transaction";
    }

    @GetMapping("/chain")
    public List<Block> getChain() {
        return blockchainService.getChain();
    }
}
