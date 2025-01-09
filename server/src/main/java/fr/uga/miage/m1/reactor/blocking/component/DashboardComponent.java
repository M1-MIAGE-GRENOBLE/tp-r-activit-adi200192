package fr.uga.miage.m1.reactor.blocking.component;

import fr.uga.miage.m1.reactor.blocking.repository.DashboardRepository;
import fr.uga.miage.m1.reactor.exception.domain.DashboardIsNotDefinedException;
import fr.uga.miage.m1.reactor.exception.technic.DashboardEntityIsAlreadyDefinedException;
import fr.uga.miage.m1.reactor.exception.technic.DashboardEntityIsNotDefinedDefinedException;
import fr.uga.miage.m1.reactor.mapper.DashboardMapper;
import fr.uga.miage.m1.reactor.model.Crypto;
import fr.uga.miage.m1.reactor.model.CryptoType;
import fr.uga.miage.m1.reactor.model.DashboardToCreate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class DashboardComponent {
    private final DashboardRepository dashboardRepository;
    private final DashboardMapper dashboardMapper;

    public Map<CryptoType, Crypto> createDashboard(DashboardToCreate dashboardToCreate) throws DashboardEntityIsAlreadyDefinedException {
        return dashboardMapper.toDashboard(dashboardRepository.create(dashboardToCreate).getCryptos());
    }

    public Map<CryptoType, Crypto> getDashboard() {
        try {
            return dashboardMapper.toDashboard(dashboardRepository.getDashboard().getCryptos());
        } catch (DashboardEntityIsNotDefinedDefinedException e) {
            throw new DashboardIsNotDefinedException(e);
        }
    }
}
