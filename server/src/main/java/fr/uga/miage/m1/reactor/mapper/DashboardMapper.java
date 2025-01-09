package fr.uga.miage.m1.reactor.mapper;

import fr.uga.miage.m1.reactor.entity.CryptoEntity;
import fr.uga.miage.m1.reactor.entity.CryptoTypeEntity;
import fr.uga.miage.m1.reactor.model.Crypto;
import fr.uga.miage.m1.reactor.model.CryptoType;
import fr.uga.miage.m1.reactor.model.DashboardToCreate;
import fr.uga.miage.m1.request.DashboardCreationRequest;
import fr.uga.miage.m1.response.CryptoResponse;
import fr.uga.miage.m1.response.CryptoTypeResponse;
import org.mapstruct.Mapper;

import java.util.Map;

@Mapper
public interface DashboardMapper {

    DashboardToCreate toDashBoardToCreate(DashboardCreationRequest dashboardToCreate);

    Map<CryptoTypeResponse, CryptoResponse> toDashboardResponse(Map<CryptoType, Crypto> dashboard);

    Map<CryptoType, Crypto> toDashboard(Map<CryptoTypeEntity, CryptoEntity> cryptos);
}
