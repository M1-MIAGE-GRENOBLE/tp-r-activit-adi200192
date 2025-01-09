package fr.uga.miage.m1.client.crypto;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.reactive.config.EnableWebFlux;

@EnableWebFlux
@SpringBootApplication
public class ClientCryptoApp {
    public static void main(String[] args) {
        SpringApplication.run(ClientCryptoApp.class, args);
    }
}

