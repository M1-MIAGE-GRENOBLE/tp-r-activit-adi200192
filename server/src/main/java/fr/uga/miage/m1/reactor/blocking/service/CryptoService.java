package fr.uga.miage.m1.reactor.blocking.service;

import fr.uga.miage.m1.reactor.blocking.component.CryptoComponent;
import fr.uga.miage.m1.reactor.model.Crypto;
import fr.uga.miage.m1.reactor.model.CryptoFluctuation;
import fr.uga.miage.m1.reactor.model.CryptoType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class CryptoService {
    private final CryptoComponent cryptoComponent;


    public Collection<Crypto> getAll() {
        return cryptoComponent.getAll();
    }

    public Crypto getCrypto(CryptoType cryptoType) {
        return cryptoComponent.getCrypto(cryptoType);
    }

    public Collection<CryptoFluctuation> getCryptoFluctuation(CryptoType type) {
        return cryptoComponent.getCryptoFluctuation(type);
    }
}
