package fr.uga.miage.m1.response;

import lombok.Data;

import java.util.Map;

@Data
public class DashboardResponse {
    Map<CryptoTypeResponse,CryptoResponse> cryptos = Map.of();
}
