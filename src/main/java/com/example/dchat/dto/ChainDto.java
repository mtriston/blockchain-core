package com.example.dchat.dto;

import com.example.dchat.model.Block;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChainDto {
    MetaDto meta;
    List<Block> chain;
}
