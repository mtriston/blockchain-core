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

    @Override
    public boolean isValidTransaction(Transaction transaction) {
        return true;
        //TODO: реализовать проверку подписи jasypt or JKS
    }

    @Override
    public boolean isValidTransactionsFromBlock(List<Transaction> transaction) {
        return true;
        // TODO: проверить транзакцию с наградой
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
        return new Transaction("System", "PUBLIC KEY", "Reward for a new block", 10);
    }
}
