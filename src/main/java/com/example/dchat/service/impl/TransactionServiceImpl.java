package com.example.dchat.service.impl;

import com.example.dchat.model.Transaction;
import com.example.dchat.repository.TransactionRepository;
import com.example.dchat.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    public static final int MINING_REWARD = 1; // to properties

    @Override
    public boolean isValidTransaction(Transaction transaction) {
        //TODO: реализовать проверку подписи jasypt or JKS

        return true;
    }

    @Override
    public boolean isValidTransactionsFromBlock(List<Transaction> transactions) {
        // TODO: проверить транзакцию с наградой
        if (transactions == null || transactions.size() <= 1 ||
                !(transactions.get(0).getSender().equals("BLOCK_CHAIN_BANK") &&
                        transactions.get(0).getAmount().equals(MINING_REWARD) &&
                        transactions.get(0).getFee().equals(0))) {
            return false;
        }
        return transactions.stream()
                .skip(1)
                .allMatch(this::isValidTransaction);
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
        return new Transaction("System", "PUBLIC KEY", "Reward for a new block", 10, 0.0);
    }
}
