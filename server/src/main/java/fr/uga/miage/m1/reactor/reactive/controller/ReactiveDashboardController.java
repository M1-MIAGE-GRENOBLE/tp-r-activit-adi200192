package fr.uga.miage.m1.reactor.reactive.controller;

import fr.uga.miage.m1.endpoint.reactive.ReactiveDashboardEndpoints;
import fr.uga.miage.m1.request.DashboardCreationRequest;
import fr.uga.miage.m1.response.CryptoFluctuationResponse;
import fr.uga.miage.m1.response.CryptoResponse;
import fr.uga.miage.m1.response.CryptoTypeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class ReactiveDashboardController implements ReactiveDashboardEndpoints {
    @Override
    public Mono<Map<CryptoTypeResponse, CryptoResponse>> createDashboardCrypto(DashboardCreationRequest dashboardToCreate) {
        return null;
    }

    @Override
    public Mono<Map<CryptoTypeResponse, CryptoFluctuationResponse>> getLastDashboardFluctuation() {
        return null;
    }

    @Override
    public Mono<Map<CryptoTypeResponse, List<CryptoFluctuationResponse>>> getAllDashboardFluctuation() {
        return null;
    }

    @Override
    public Flux<Map<CryptoTypeResponse, CryptoFluctuationResponse>> getDashboardFluctuation() {
        return null;
    }

    @Override
    public Mono<Map<CryptoTypeResponse, CryptoFluctuationResponse>> getDashboardCrypto() {
        return null;
    }
}
