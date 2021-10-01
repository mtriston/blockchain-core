package com.example.dchat;

import com.example.dchat.service.KeysGenerator;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Log4j2
@SpringBootApplication
public class DchatApplication {

    private static final int ENCRYPTION_KEY_LENGTH = 1024; // to properties

    private static void generateEncryptionKeys() {
        // TODO: check if there is key pair in ./secrets directory

        try {
            new KeysGenerator(ENCRYPTION_KEY_LENGTH)
                    .generateKeyPair();
        } catch (Exception e) {
            log.debug(e.getMessage());
        }

    }

    public static void main(String[] args) {

        generateEncryptionKeys();
        SpringApplication.run(DchatApplication.class, args);
    }

}
