package com.example.dchat.service;

import com.example.dchat.model.Transaction;

import java.util.List;

public interface TransactionService {
    boolean isValidTransaction(Transaction transaction);
    boolean isUniqueTransaction(Transaction transaction);
    void addTransaction(Transaction transaction);
    void removeTransactions(List<Transaction> transactions);
    List<Transaction> getTransactions(int n);
    boolean hasRequiredTransactions(int n);
}
