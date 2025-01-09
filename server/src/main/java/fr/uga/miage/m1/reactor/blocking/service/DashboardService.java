package fr.uga.miage.m1.reactor.blocking.service;

import fr.uga.miage.m1.reactor.blocking.component.DashboardComponent;
import fr.uga.miage.m1.reactor.exception.domain.DashboardAlreadyDefinedException;
import fr.uga.miage.m1.reactor.exception.technic.DashboardEntityIsAlreadyDefinedException;
import fr.uga.miage.m1.reactor.mapper.DashboardMapper;
import fr.uga.miage.m1.reactor.model.Crypto;
import fr.uga.miage.m1.reactor.model.CryptoType;
import fr.uga.miage.m1.request.DashboardCreationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class DashboardService {
    private final DashboardComponent dashboardComponent;
    private final DashboardMapper dashboardMapper;

    public Map<CryptoType, Crypto> createDashboard(DashboardCreationRequest dashboardToCreate) {
        try {
            return dashboardComponent.createDashboard(dashboardMapper.toDashBoardToCreate(dashboardToCreate));
        } catch (DashboardEntityIsAlreadyDefinedException e) {
            throw new DashboardAlreadyDefinedException(e);
        }
    }

    public Map<CryptoType, Crypto> getDashboard() {
        return dashboardComponent.getDashboard();
    }
}
