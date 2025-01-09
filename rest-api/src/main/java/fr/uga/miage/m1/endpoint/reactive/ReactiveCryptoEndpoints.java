package fr.uga.miage.m1.endpoint.reactive;

import fr.uga.miage.m1.response.CryptoFluctuationResponse;
import fr.uga.miage.m1.response.CryptoResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequestMapping("/reactive/crypto/")
public interface ReactiveCryptoEndpoints {

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/all")
    Flux<CryptoResponse> cryptos();

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{name}")
    Mono<CryptoResponse> getCrypto(@PathVariable("name") String name);

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{name}/fluctuation")
    Flux<CryptoFluctuationResponse> getFluctuation(@PathVariable("name") String name);


}
