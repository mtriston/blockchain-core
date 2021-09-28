package com.example.dchat.dto;

import com.example.dchat.model.Block;
import lombok.Data;

import java.util.List;

@Data
public class ChainDto {
    MetaDto meta;
    List<Block> chain;
}
