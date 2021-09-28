package com.example.dchat.dto;

import com.example.dchat.model.Transaction;
import lombok.Data;

@Data
public class TransactionDto {
    MetaDto meta;
    Transaction transaction;
}
