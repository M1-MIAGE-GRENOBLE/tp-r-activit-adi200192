package fr.uga.miage.m1.reactor.config;

import fr.uga.miage.m1.reactor.client.CryptoClient;
import fr.uga.miage.m1.reactor.client.model.ReactiveCryptoClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class WebClientConfig {


    @Bean
    public CryptoClient getCryptoClient(@Value("${client.url}") String url) {
        HttpServiceProxyFactory httpServiceProxyFactory =
                HttpServiceProxyFactory
                        .builderFor(WebClientAdapter.create(WebClient.create(url)))
                        .build();

        return httpServiceProxyFactory.createClient(CryptoClient.class);
    }

    @Bean
    public ReactiveCryptoClient getReactiveCryptoClient(@Value("${client.url}") String url) {
        HttpServiceProxyFactory httpServiceProxyFactory =
                HttpServiceProxyFactory
                        .builderFor(WebClientAdapter.create(WebClient.create(url)))
                        .build();

        return httpServiceProxyFactory.createClient(ReactiveCryptoClient.class);
    }
}
