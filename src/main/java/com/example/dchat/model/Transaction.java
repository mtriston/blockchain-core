package com.example.dchat.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transaction { // why fields are package-private but not private?
    // private String senderLogin; // to print message like: [spivak] Hello, bitches!
    // private boolean isPrivate; // if true -> it is for msgs for exact person (only recipient will see it)
                                // if false -> it is printed in public chat -> so everybody see it

    String sender; // is it ip? or public key? or login? or some client_id?
    String recipient; // it can be null!! -> then it is broadcasting (for everyone).
    String message; // message should be encrypted with public key of the recipient
    int amount; // 1. this should be double; 2. this should be an amount of coins in the transfer (see explanation below)
    // So when the peer mines the new block -> he also adds to the new block the following transaction:
    // sender: holy_blockchain; recipient: me; message: null; amount: 1 coin; fee: 0;
    // private double fee; // it is commission of the transaction -> if I want my message to be published fast -> I pay coins
}
