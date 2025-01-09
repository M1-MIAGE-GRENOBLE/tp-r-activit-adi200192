package fr.uga.miage.m1.response;

import lombok.Data;

import java.util.Map;

@Data
public class DashboardFluctuationResponse {
    Map<CryptoTypeResponse,CryptoFluctuationResponse> dashboardCrytos;
}
