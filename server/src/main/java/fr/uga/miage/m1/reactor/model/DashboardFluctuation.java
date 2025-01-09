package fr.uga.miage.m1.reactor.model;

import fr.uga.miage.m1.response.CryptoFluctuationResponse;
import fr.uga.miage.m1.response.CryptoTypeResponse;
import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class DashboardFluctuation {
    private Map<CryptoTypeResponse, CryptoFluctuationResponse> dashboardCryptoFluctuation;
}
