package fr.uga.miage.m1.reactor.client;

import fr.uga.miage.m1.client.crypto.endpoint.ClientCryptoEndpoints;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange
public interface CryptoClient extends ClientCryptoEndpoints {

}
