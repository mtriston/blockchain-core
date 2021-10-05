package com.example.blockchain.dto;

import com.example.blockchain.model.Block;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlockDto {
    MetaDto meta;
    Block block;
}
