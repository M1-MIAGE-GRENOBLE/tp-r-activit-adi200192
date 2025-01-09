package fr.uga.miage.m1.reactor.model;

import lombok.Builder;
import lombok.Data;

import java.util.Collection;
import java.util.List;

@Data
@Builder
public class Dashboard {
    String name;
    Collection<CryptoType> cryptos = List.of();
}
