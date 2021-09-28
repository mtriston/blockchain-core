package com.example.dchat.dto;

import com.example.dchat.model.Block;
import lombok.Data;

@Data
public class BlockDto {
    MetaDto meta;
    Block block;
}
