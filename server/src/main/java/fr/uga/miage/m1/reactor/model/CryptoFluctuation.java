package fr.uga.miage.m1.reactor.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CryptoFluctuation {
    Double currentPrice;
}
