package fr.uga.miage.m1.reactor.model;

import lombok.Data;

import java.util.LinkedList;

@Data
public class Crypto {
    CryptoType type;
    String name;
    Integer safetyIndex;
    LinkedList<CryptoFluctuation> fluctuations;
    boolean externeSource;
}
