package fr.uga.miage.m1.reactor.mapper;

import fr.uga.miage.m1.reactor.entity.CryptoEntity;
import fr.uga.miage.m1.reactor.entity.CryptoFluctuationEntity;
import fr.uga.miage.m1.reactor.model.Crypto;
import fr.uga.miage.m1.reactor.model.CryptoFluctuation;
import fr.uga.miage.m1.reactor.model.Dashboard;
import fr.uga.miage.m1.request.DashboardCreationRequest;
import fr.uga.miage.m1.response.CryptoFluctuationResponse;
import fr.uga.miage.m1.response.CryptoResponse;
import org.mapstruct.Mapper;

@Mapper
public interface CryptoMapper {
    Dashboard toDashboardToCreate(DashboardCreationRequest dashboardToCreate);

    Crypto toCryto(CryptoEntity crypto);

    CryptoResponse toCrytoResponse(Crypto c);

    CryptoFluctuation toCryptoFluctuation(CryptoFluctuationEntity entity);

    CryptoFluctuationResponse toCryptoFluctuationResponse(CryptoFluctuation c);

    CryptoFluctuation toCryptoFluctuation(fr.uga.miage.m1.client.crypto.response.CryptoFluctuation clientFluctuation);
}
