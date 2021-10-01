package com.example.dchat.dto;

import com.example.dchat.model.Block;
import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class BlockDto {
    MetaDto meta;
    Block block;
}
