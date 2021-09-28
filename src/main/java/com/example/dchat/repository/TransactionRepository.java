package com.example.dchat.repository;

import com.example.dchat.model.Transaction;

import java.util.List;

public interface TransactionRepository {
    void addTransaction(Transaction transaction);

    void removeTransactions(List<Transaction> transactions);

    List<Transaction> getTransactions(int n);
}
