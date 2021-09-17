package com.example.dchat.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Transaction {
    String sender;
    String recipient;
    String message;
    int amount;
}
