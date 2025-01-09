package fr.uga.miage.m1.client.crypto.service;

import fr.uga.miage.m1.client.crypto.component.ClientCryptoComponent;
import fr.uga.miage.m1.client.crypto.request.ClientCryptoType;
import fr.uga.miage.m1.client.crypto.response.CryptoFluctuation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ClientCryptoService {
    private final ClientCryptoComponent clientCryptoComponent;

    public CryptoFluctuation getLastCryptoFluctuation(ClientCryptoType clientCryptoType) {
        return clientCryptoComponent.getLastCryptoFluctuation();
    }

    public Mono<CryptoFluctuation> getLastCryptoFluctuation() {
        return clientCryptoComponent.getReactiveLastCryptoFluctuation();
    }

    public Flux<CryptoFluctuation> getCryptoFluctuation(ClientCryptoType clientCryptoType) {
        return clientCryptoComponent.getCryptoFluctuation();
    }
}
