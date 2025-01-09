package fr.uga.miage.m1.client.crypto.endpoint;

import fr.uga.miage.m1.client.crypto.request.ClientCryptoTypeRequest;
import fr.uga.miage.m1.client.crypto.response.CryptoFluctuation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequestMapping("/client/crypto/reactive/")
public interface ReactiveClientCryptoEndpoints {

    @GetMapping("fluctuation/last")
    Mono<CryptoFluctuation> getLastCryptoFluctuation(@RequestBody ClientCryptoTypeRequest request);


    @GetMapping(value = "fluctuation",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    Flux<CryptoFluctuation> getCryptoFluctuation(ClientCryptoTypeRequest requests);
}
