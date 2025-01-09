package fr.uga.miage.m1.reactor.blocking.repository;

import fr.uga.miage.m1.reactor.entity.CryptoEntity;
import fr.uga.miage.m1.reactor.entity.CryptoFluctuationEntity;
import fr.uga.miage.m1.reactor.entity.CryptoTypeEntity;
import fr.uga.miage.m1.reactor.model.CryptoType;
import org.springframework.stereotype.Repository;

import java.security.SecureRandom;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;
import java.util.Random;

@Repository
public class CryptoRepository {
    private final Map<CryptoTypeEntity, CryptoEntity> cryptos = Map.of(
            CryptoTypeEntity.BITCOIN, CryptoEntity
                    .builder()
                    .name("Bitcoin BTC")
                    .type(CryptoTypeEntity.BITCOIN)
                    .externeSource(true)
                    .safetyIndex(1)
                    .cryptoFluctuationEntities(new LinkedList<>())
                    .build(),

            CryptoTypeEntity.CARDANO, CryptoEntity
                    .builder()
                    .name("Cardano CRD")
                    .type(CryptoTypeEntity.CARDANO)
                    .externeSource(false)
                    .safetyIndex(1)
                    .cryptoFluctuationEntities(new LinkedList<>())
                    .build(),
            CryptoTypeEntity.DODGE_COIN, CryptoEntity
                    .builder()
                    .name("Dodge coin DC")
                    .type(CryptoTypeEntity.DODGE_COIN)
                    .externeSource(true)
                    .safetyIndex(6)
                    .cryptoFluctuationEntities(new LinkedList<>())
                    .build(),

            CryptoTypeEntity.ETHEREUM, CryptoEntity
                    .builder()
                    .name("Ethereum ETH")
                    .type(CryptoTypeEntity.ETHEREUM)
                    .externeSource(true)
                    .safetyIndex(1)
                    .cryptoFluctuationEntities(new LinkedList<>())
                    .build(),
            CryptoTypeEntity.SOLANA, CryptoEntity
                    .builder()
                    .name("Solana SLN")
                    .type(CryptoTypeEntity.SOLANA)
                    .externeSource(true)
                    .safetyIndex(1)
                    .cryptoFluctuationEntities(new LinkedList<>())
                    .build(),
            CryptoTypeEntity.PEPE, CryptoEntity
                    .builder()
                    .name("Pepe")
                    .type(CryptoTypeEntity.PEPE)
                    .externeSource(false)
                    .safetyIndex(8)
                    .cryptoFluctuationEntities(new LinkedList<>())
                    .build()
    );

    public Collection<CryptoEntity> getAll() {
        return cryptos.values();
    }

    public CryptoEntity getCrypto(CryptoType cryptoType) {
        return cryptos.get(CryptoTypeEntity.valueOf(cryptoType.name()));
    }

    public Collection<CryptoFluctuationEntity> getCryptoFluctuation(CryptoType type) {
        CryptoEntity entity = cryptos.get(CryptoTypeEntity.valueOf(type.name()));
        double latPrice = getCryptoLastPrice(entity.getCryptoFluctuationEntities());
        entity.getCryptoFluctuationEntities().addLast(CryptoFluctuationEntity.builder().currentPrice(latPrice).build());
        return entity.getCryptoFluctuationEntities();
    }

    private double getCryptoLastPrice(LinkedList<CryptoFluctuationEntity> cryptoFluctuationEntity) {
        double price = 10D;
        if (cryptoFluctuationEntity.peekLast() != null) {
            price = cryptoFluctuationEntity.peekLast().getCurrentPrice();
        }

        double mu = 0.0005;            // Rendement moyen (drift)
        double sigma = 0.02;           // Volatilité
        int stepsPerDay = 1;           // Nombre de pas par jour (peut être ajusté)
        double dt = 1.0 / stepsPerDay; // Intervalle de temps

        // Générateur aléatoire
        Random random = new SecureRandom();
        double gaussian = random.nextGaussian();

        price *= Math.exp((mu - 0.5 * sigma * sigma) * dt + sigma * Math.sqrt(dt) * gaussian);
        return price;
    }
}
