package com.example.dchat.dto;

import com.example.dchat.model.Peer;
import lombok.Data;

import java.util.List;

@Data
public class PeerListDto {
    MetaDto meta;
    List<Peer> peerList;
}
