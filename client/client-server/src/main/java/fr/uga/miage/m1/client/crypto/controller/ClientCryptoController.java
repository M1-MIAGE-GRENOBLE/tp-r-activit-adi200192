package fr.uga.miage.m1.client.crypto.controller;

import fr.uga.miage.m1.client.crypto.endpoint.ClientCryptoEndpoints;
import fr.uga.miage.m1.client.crypto.request.ClientCryptoTypeRequest;
import fr.uga.miage.m1.client.crypto.response.CryptoFluctuation;
import fr.uga.miage.m1.client.crypto.service.ClientCryptoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ClientCryptoController implements ClientCryptoEndpoints {
    private final ClientCryptoService clientCryptoService;

    @Override
    public CryptoFluctuation getLastCryptoFluctuation(ClientCryptoTypeRequest request) {
        return clientCryptoService.getLastCryptoFluctuation(request.clientCryptoType());
    }

    @Override
    public Collection<CryptoFluctuation> getAllCryptoFluctuation(ClientCryptoTypeRequest request) {
        return List.of();
    }
}
