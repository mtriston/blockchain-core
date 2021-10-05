package com.example.blockchain.dto;

import com.example.blockchain.model.Transaction;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDto {
    MetaDto meta;
    Transaction transaction;
}
