package com.example.dchat.repository;

import com.example.dchat.model.Transaction;

import java.util.List;

public interface TransactionRepository {

    List<Transaction> getTransactions();

    void addTransaction(Transaction transaction);

    void removeTransactions(List<Transaction> transactions);

    boolean isContains(Transaction transaction);
}
