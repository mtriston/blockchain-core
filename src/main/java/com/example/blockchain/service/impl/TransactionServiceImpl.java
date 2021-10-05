package com.example.blockchain.service.impl;

import com.example.blockchain.repository.TransactionRepository;
import com.example.blockchain.model.Transaction;
import com.example.blockchain.service.CryptographyService;
import com.example.blockchain.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    @Value("${mining.reward.amount}")
    private Double MINING_REWARD_AMOUNT;
    @Value("${mining.reward.sender}")
    private String MINING_REWARD_SENDER;

    private final TransactionRepository transactionRepository;
    private final CryptographyService cryptographyService;

    @Override
    public boolean isValidTransaction(Transaction transaction) {
        return cryptographyService.verifyDigitalSignature(transaction.getMessage(), transaction.getSender()) != null &&
                transaction.getAmount() >= 0 && transaction.getFee() >= 0;
    }

    @Override
    public boolean isValidTransactionsFromBlock(List<Transaction> transactions) {
        return transactions != null &&
            transactions.size() > 1 &&
            transactions.get(0).getSender().equals(MINING_REWARD_SENDER) &&
            transactions.get(0).getAmount().equals(MINING_REWARD_AMOUNT) &&
            transactions.stream().skip(1).allMatch(this::isValidTransaction);
    }

    @Override
    public boolean isContains(Transaction transaction) {
        return transactionRepository.isContains(transaction);
    }

    @Override
    public void addTransaction(Transaction transaction) {
        log.debug("Added a new transaction in pending pool: " + transaction);
        transactionRepository.addTransaction(transaction);
    }

    @Override
    public void removeTransactions(List<Transaction> transactions) {
        log.debug("Removed transactions from pending pool: " + transactions);
        transactionRepository.removeTransactions(transactions);
    }

    @Override
    public List<Transaction> getTransactions() {
        return transactionRepository.getTransactions();
    }

    @Override
    public Transaction createRewardTransaction() {
        //TODO: подумать, нужно ли что-то шифровать/подписывать
        //TODO: в получателя установить public key текущего узла.
        //TODO: вынести значения в константы, когда станет понятно, как это должно выглядеть
        return new Transaction(MINING_REWARD_SENDER, "PUBLIC KEY", "Reward for a new block", MINING_REWARD_AMOUNT, 0.0);
    }
}
