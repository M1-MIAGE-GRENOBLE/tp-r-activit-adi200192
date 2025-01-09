package fr.uga.miage.m1.reactor.client.model;

import lombok.Data;

@Data
public class CryptoFluctuationDTO {
    ClientCryptoTypeDTO clientCryptoTypeDTO;
    double price;
}
