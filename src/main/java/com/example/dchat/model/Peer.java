package com.example.dchat.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;
import java.util.Objects;

@Data
@AllArgsConstructor
public class Peer {
    private String ip;
    private int port;
    Date lastSeen;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Peer peer = (Peer) o;
        return port == peer.port && ip.equals(peer.ip);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ip, port);
    }
}
