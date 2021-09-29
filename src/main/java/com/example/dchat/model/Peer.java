package com.example.dchat.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Objects;

@Data
@NoArgsConstructor
public class Peer {
    // private String login; // may be this will be some kind of registration. And how the fuck will you know the sender else?
    // If we have dchat -> so the point is -> it should store every word of every person. (changeless history)
    // But how would we know the person with dynamical id?
    // And of course login should be unique! Or not... :-(
    // ---------------------------------------------------- new idea:
    // login + login, encrypted with private key of owner!
    // Every Peer entity already have 'public key' field
    // SOLUTION: every message SHOULD BE not only encrypted with
    // private key or recipient (if the message have the recipient) -> it also should be SIGNED with
    // private key of the sender!!!
    // So we need to validate the users login
    private String ip; // it can and would be dynamical in most cases, so we need permanent login
    private int port;
    Date lastSeen;
    // private String publicKey; // we need this to encrypt messages, don't we?

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
        return port == peer.port && ip.equals(peer.ip); // + login? else -> if ip is dynamical (it is) and smb else will get it?
        // or uniqueness of the peer is login+public key?

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
