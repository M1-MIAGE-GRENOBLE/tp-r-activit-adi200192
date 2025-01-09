package fr.uga.miage.m1.reactor.blocking.repository;

import fr.uga.miage.m1.reactor.entity.CryptoEntity;
import fr.uga.miage.m1.reactor.entity.CryptoTypeEntity;
import fr.uga.miage.m1.reactor.entity.DashboardEntity;
import fr.uga.miage.m1.reactor.exception.technic.DashboardEntityIsAlreadyDefinedException;
import fr.uga.miage.m1.reactor.exception.technic.DashboardEntityIsNotDefinedDefinedException;
import fr.uga.miage.m1.reactor.model.DashboardToCreate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.EnumMap;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class DashboardRepository {
    private final CryptoRepository cryptoRepository;
    private DashboardEntity dashboardEntity = null;

    public DashboardEntity create(DashboardToCreate dashboardToCreate) throws DashboardEntityIsAlreadyDefinedException {
        if(this.dashboardEntity != null){
            throw new DashboardEntityIsAlreadyDefinedException();
        }
        Map<CryptoTypeEntity, CryptoEntity> newDashboard = new EnumMap<>(CryptoTypeEntity.class);
        dashboardToCreate.getCryptos().stream().map(cryptoRepository::getCrypto).map(
                cryptoEntity -> Map.entry(cryptoEntity.getType(),cryptoEntity)
        ).forEach(cryptoTypeEntityCryptoEntityEntry -> newDashboard.put(cryptoTypeEntityCryptoEntityEntry.getKey(), cryptoTypeEntityCryptoEntityEntry.getValue()));
        dashboardEntity = DashboardEntity.builder().cryptos(newDashboard).build();
        return this.dashboardEntity;
    }

    public DashboardEntity getDashboard() throws DashboardEntityIsNotDefinedDefinedException {
        if(this.dashboardEntity == null){
            throw new DashboardEntityIsNotDefinedDefinedException();
        }
        return this.dashboardEntity;
    }

    public void deleteDashboard() {
        this.dashboardEntity = null;
    }
}
