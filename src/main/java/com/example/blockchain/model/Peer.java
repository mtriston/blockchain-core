package com.example.blockchain.model;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Data
@AllArgsConstructor
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
    String address;
    LocalDateTime lastSeen;
    // private String publicKey; // we need this to encrypt messages, don't we?

    public Peer(@NonNull String address) {
        this.address = address;
        this.lastSeen = LocalDateTime.now();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Peer peer = (Peer) o;
        return address.equals(peer.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(address);
    }

    @Override
    public String toString() {
        return address;
    }
}
