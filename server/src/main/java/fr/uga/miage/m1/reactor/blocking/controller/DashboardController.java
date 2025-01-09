package fr.uga.miage.m1.reactor.blocking.controller;

import fr.uga.miage.m1.endpoint.blocking.DashboardEndpoints;
import fr.uga.miage.m1.reactor.blocking.service.DashboardService;
import fr.uga.miage.m1.reactor.mapper.DashboardMapper;
import fr.uga.miage.m1.request.DashboardCreationRequest;
import fr.uga.miage.m1.response.CryptoFluctuationResponse;
import fr.uga.miage.m1.response.CryptoResponse;
import fr.uga.miage.m1.response.CryptoTypeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class DashboardController implements DashboardEndpoints {
    private final DashboardService dashboardService;
    private final DashboardMapper dashboardMapper;

    @Override
    public Map<CryptoTypeResponse, CryptoResponse> createDashboardCrypto(DashboardCreationRequest dashboardToCreate) {
        return dashboardMapper.toDashboardResponse(dashboardService.createDashboard(dashboardToCreate));
    }

    @Override
    public Map<CryptoTypeResponse, CryptoFluctuationResponse> getLastDashboardFluctuation() {
        return Map.of();
    }

    @Override
    public Map<CryptoTypeResponse, List<CryptoFluctuationResponse>> getAllDashboardFluctuation() {
        return Map.of();
    }

    @Override
    public Map<CryptoTypeResponse, CryptoResponse> getDashboardCrypto() {
        return dashboardMapper.toDashboardResponse(dashboardService.getDashboard());
    }
}
