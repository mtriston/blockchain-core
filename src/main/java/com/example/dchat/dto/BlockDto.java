package com.example.dchat.dto;

import com.example.dchat.model.Block;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlockDto {
    MetaDto meta;
    Block block;
}
