package com.example.dchat.dto;

import com.example.dchat.model.Transaction;
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
