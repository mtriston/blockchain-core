package com.example.dchat.dto;

import com.example.dchat.model.Block;
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
