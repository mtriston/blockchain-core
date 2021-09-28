package com.example.dchat.service.impl;

import com.example.dchat.model.Transaction;
import com.example.dchat.repository.TransactionRepository;
import com.example.dchat.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;

    @Override
    public boolean isValidTransaction(Transaction transaction) {
        return true;
        //TODO: реализовать проверку подписи
    }

    @Override
    public boolean isContains(Transaction transaction) {
        return transactionRepository.isContains(transaction);
    }

    @Override
    public void addTransaction(Transaction transaction) {
        transactionRepository.addTransaction(transaction);
    }

    @Override
    public void removeTransactions(List<Transaction> transactions) {
        transactionRepository.removeTransactions(transactions);
    }

    @Override
    public List<Transaction> getTransactions() {
        return transactionRepository.getTransactions();
    }
}
