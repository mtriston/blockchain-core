package com.example.dchat.dto;

import lombok.AllArgsConstructor;
import lombok.Value;

import java.util.List;

@Value
@AllArgsConstructor
public class PeerListDto {
    MetaDto meta;
    List<String> peerList;
}
