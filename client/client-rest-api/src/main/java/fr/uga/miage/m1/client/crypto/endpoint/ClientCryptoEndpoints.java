package fr.uga.miage.m1.client.crypto.endpoint;

import fr.uga.miage.m1.client.crypto.request.ClientCryptoTypeRequest;
import fr.uga.miage.m1.client.crypto.response.CryptoFluctuation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collection;

@RequestMapping("/client/crypto/")
public interface ClientCryptoEndpoints {
    @GetMapping(value = "fluctuation/last")
    CryptoFluctuation getLastCryptoFluctuation(@RequestBody ClientCryptoTypeRequest request);

    @GetMapping(value = "fluctuation")
    Collection<CryptoFluctuation> getAllCryptoFluctuation(@RequestBody ClientCryptoTypeRequest request);
}
