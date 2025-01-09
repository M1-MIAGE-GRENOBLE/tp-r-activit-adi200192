package fr.uga.miage.m1.reactor.blocking.component;

import fr.uga.miage.m1.client.crypto.request.ClientCryptoType;
import fr.uga.miage.m1.client.crypto.request.ClientCryptoTypeRequest;
import fr.uga.miage.m1.reactor.blocking.repository.CryptoRepository;
import fr.uga.miage.m1.reactor.client.CryptoClient;
import fr.uga.miage.m1.reactor.entity.CryptoEntity;
import fr.uga.miage.m1.reactor.mapper.CryptoMapper;
import fr.uga.miage.m1.reactor.model.Crypto;
import fr.uga.miage.m1.reactor.model.CryptoFluctuation;
import fr.uga.miage.m1.reactor.model.CryptoType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CryptoComponent {
    private final CryptoRepository cryptoRepository;
    private final CryptoMapper cryptoMapper;
    private final CryptoClient cryptoClient;

    public Collection<Crypto> getAll() {
        return cryptoRepository.getAll()
                .stream()
                .map(cryptoMapper::toCryto)
                .collect(Collectors.toSet());
    }

    public Crypto getCrypto(CryptoType cryptoType) {
        return cryptoMapper.toCryto(cryptoRepository.getCrypto(cryptoType));
    }

    public Collection<CryptoFluctuation> getCryptoFluctuation(CryptoType type) {
        CryptoEntity entity = cryptoRepository.getCrypto(type);
        if (entity.isExterneSource()) {
            return cryptoClient.getAllCryptoFluctuation(ClientCryptoTypeRequest.builder()
                    .clientCryptoType(ClientCryptoType.valueOf(entity.getType().name()))
                    .build()).stream().map(cryptoMapper::toCryptoFluctuation).toList();
        } else {
            return cryptoRepository
                    .getCryptoFluctuation(type)
                    .stream()
                    .map(cryptoMapper::toCryptoFluctuation)
                    .toList();
        }
    }
}
