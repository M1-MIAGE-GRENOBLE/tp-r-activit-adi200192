package fr.uga.miage.m1.reactor.model;

import lombok.Builder;
import lombok.Data;

import java.util.Collection;

@Data
@Builder
public class DashboardToCreate {
    Collection<CryptoType> cryptos;
}
