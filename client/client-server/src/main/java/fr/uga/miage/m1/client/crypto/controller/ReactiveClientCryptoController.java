package fr.uga.miage.m1.client.crypto.controller;

import fr.uga.miage.m1.client.crypto.endpoint.ReactiveClientCryptoEndpoints;
import fr.uga.miage.m1.client.crypto.request.ClientCryptoTypeRequest;
import fr.uga.miage.m1.client.crypto.response.CryptoFluctuation;
import fr.uga.miage.m1.client.crypto.service.ClientCryptoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.config.EnableWebFlux;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@EnableWebFlux
@RestController
@RequiredArgsConstructor
public class ReactiveClientCryptoController implements ReactiveClientCryptoEndpoints {
    private final ClientCryptoService clientCryptoService;


    @Override
    public Mono<CryptoFluctuation> getLastCryptoFluctuation(ClientCryptoTypeRequest request) {
        return clientCryptoService.getLastCryptoFluctuation();
    }

    @Override
    public Flux<CryptoFluctuation> getCryptoFluctuation(ClientCryptoTypeRequest request) {
        return clientCryptoService.getCryptoFluctuation(request.clientCryptoType());
    }
}
