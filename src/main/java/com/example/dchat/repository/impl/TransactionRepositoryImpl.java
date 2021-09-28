package com.example.dchat.repository.impl;

import com.example.dchat.model.Transaction;
import com.example.dchat.repository.TransactionRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Repository
public class TransactionRepositoryImpl implements TransactionRepository {
    private final List<Transaction> pendingTransactions = Collections.synchronizedList(new ArrayList<>());

    @Override
    public void addTransaction(Transaction transaction) {
        pendingTransactions.add(transaction);
    }

    @Override
    public void removeTransactions(List<Transaction> transactions) {
        pendingTransactions.removeAll(transactions);
    }

    @Override
    public List<Transaction> getTransactions(int n) {
        return pendingTransactions.subList(0, n);
    }
}
