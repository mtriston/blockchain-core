package com.example.blockchain.service;

import com.example.blockchain.model.Transaction;

import java.util.List;

public interface TransactionService {
    boolean isValidTransaction(Transaction transaction);
    boolean isValidTransactionsFromBlock(List<Transaction> transaction);
    boolean isContains(Transaction transaction);
    void addTransaction(Transaction transaction);
    void removeTransactions(List<Transaction> transactions);
    List<Transaction> getTransactions();
    Transaction createRewardTransaction();
}
