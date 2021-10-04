package com.example.dchat.dto;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class PingDto {
    MetaDto meta;
    int chainHeight;
}
