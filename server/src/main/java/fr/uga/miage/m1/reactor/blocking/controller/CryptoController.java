package fr.uga.miage.m1.reactor.blocking.controller;

import fr.uga.miage.m1.endpoint.blocking.CryptoEndpoints;
import fr.uga.miage.m1.reactor.blocking.service.CryptoService;
import fr.uga.miage.m1.reactor.mapper.CryptoMapper;
import fr.uga.miage.m1.reactor.model.CryptoType;
import fr.uga.miage.m1.request.CryptoTypeRequest;
import fr.uga.miage.m1.response.CryptoFluctuationResponse;
import fr.uga.miage.m1.response.CryptoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class CryptoController implements CryptoEndpoints {
    private final CryptoService cryptoService;
    private final CryptoMapper cryptoMapper;

    @Override
    public Collection<CryptoResponse> cryptos() {
        return cryptoService.getAll()
                .stream()
                .map(cryptoMapper::toCrytoResponse)
                .collect(Collectors.toSet());
    }

    @Override
    public CryptoResponse getCrypto(CryptoTypeRequest type) {
        return cryptoMapper.toCrytoResponse(cryptoService.getCrypto(CryptoType.valueOf(type.name())));
    }

    @Override
    public Collection<CryptoFluctuationResponse> getFluctuation(CryptoTypeRequest type) {
        return cryptoService.getCryptoFluctuation(CryptoType.valueOf(type.name()))
                .stream()
                .map(cryptoMapper::toCryptoFluctuationResponse)
                .toList();
    }
}
