package fr.uga.miage.m1.reactor.entity;

import lombok.Builder;
import lombok.Data;

import java.util.LinkedList;

@Data
@Builder
public class CryptoEntity {
    CryptoTypeEntity type;
    String name;
    Integer safetyIndex;
    boolean externeSource;
    LinkedList<CryptoFluctuationEntity> cryptoFluctuationEntities;
}
