package fr.uga.miage.m1.endpoint.blocking;

import fr.uga.miage.m1.request.CryptoTypeRequest;
import fr.uga.miage.m1.response.CryptoFluctuationResponse;
import fr.uga.miage.m1.response.CryptoResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Collection;

@RequestMapping("/crypto")
public interface CryptoEndpoints {

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/all")
    Collection<CryptoResponse> cryptos();

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{type}")
    CryptoResponse getCrypto(@PathVariable("type") CryptoTypeRequest type);

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{type}/fluctuation/all")
    Collection<CryptoFluctuationResponse> getFluctuation(@PathVariable("type") CryptoTypeRequest type);


}
