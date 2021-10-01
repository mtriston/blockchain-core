package com.example.dchat.dto;

import com.example.dchat.model.Transaction;
import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class TransactionDto {
    MetaDto meta;
    Transaction transaction;
}
