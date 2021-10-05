package com.example.blockchain.repository;

import com.example.blockchain.model.Transaction;

import java.util.List;

public interface TransactionRepository {

    List<Transaction> getTransactions();

    void addTransaction(Transaction transaction);

    void removeTransactions(List<Transaction> transactions);

    boolean isContains(Transaction transaction);
}
