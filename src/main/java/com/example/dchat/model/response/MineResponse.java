package com.example.dchat.model.response;

import com.example.dchat.model.Transaction;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class MineResponse {
    private String message;
    private int index;
    private List<Transaction> transactions;
    private int proof;
    private String previousHash;
}