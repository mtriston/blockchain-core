package com.example.dchat.dto;

import com.example.dchat.model.Peer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PeerListDto {
    MetaDto meta;
    List<Peer> peerList;
}
