package com.example.dchat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MetaDto { // why do we need this anyway?
                        // pingDto.getMeta().getSenderAddress() looks odd.
                        // why not just pingDto.getSenderAddress()?
    String senderAddress;
}
