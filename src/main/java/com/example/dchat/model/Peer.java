package com.example.dchat.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Objects;

@Data
@NoArgsConstructor
public class Peer {
    private String ip;
    private int port;
    Date lastSeen;

    public Peer(String address) {
        this.ip = address.split(":")[0];
        this.port = Integer.parseInt(address.split(":")[1]);
        this.lastSeen = new Date(System.currentTimeMillis());
    }

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

    @Override
    public String toString() {
        return ip + ":" + port;
    }
}
