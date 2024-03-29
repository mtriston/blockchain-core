package com.example.blockchain.controller;

import com.example.blockchain.dto.*;
import com.example.blockchain.service.BlockchainFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequiredArgsConstructor
public class BlockchainController {
    private final BlockchainFacade blockchainFacade;

    @PostMapping("/transaction")
    public String postTransaction(@RequestBody TransactionDto transactionDto) {
        blockchainFacade.handleTransaction(transactionDto);
        return "Transaction is received";
    }

    @PostMapping("/block")
    public String postBlock(@RequestBody BlockDto blockDto) {
        blockchainFacade.handleBlock(blockDto);
        return "Block is received";
    }

    @PostMapping("/peer")
    public String postPeerList(@RequestBody PeerListDto peerListDto) {
        blockchainFacade.handlePeerList(peerListDto);
        return "Peers are received";
    }

    @PostMapping("/ping") // what does this name mean? new person registration?
    public String postPing(@RequestBody PingDto pingDto) {
        blockchainFacade.handlePing(pingDto);
        return "Ping are received";
    }

    @GetMapping("/chain")
    ChainDto getChain() {
        return blockchainFacade.getChain();
    }
}
