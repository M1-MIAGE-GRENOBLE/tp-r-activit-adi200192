package fr.uga.miage.m1.reactor.entity;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class DashboardEntity {
    Map<CryptoTypeEntity,CryptoEntity> cryptos;
}
