package com.example.dchat;

import com.example.dchat.service.KeysGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

@SpringBootApplication
public class DchatApplication {

    private static final int ENCRYPTION_KEY_LENGTH = 1024; // to properties

    private static void generateEncryptionKeys() {
        try {
            // TODO: check if there is key pair in ./secrets directory

            new KeysGenerator(ENCRYPTION_KEY_LENGTH)
                    .generateKeyPair();
        }
        catch (NoSuchAlgorithmException | NoSuchProviderException e){
            System.err.println(e.getMessage());
        }
    }

    public static void main(String[] args) {

        generateEncryptionKeys();
        SpringApplication.run(DchatApplication.class, args);
    }

}
