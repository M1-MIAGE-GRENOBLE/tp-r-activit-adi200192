package fr.uga.miage.m1.reactor.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CryptoFluctuationEntity {
    Double currentPrice;
}
