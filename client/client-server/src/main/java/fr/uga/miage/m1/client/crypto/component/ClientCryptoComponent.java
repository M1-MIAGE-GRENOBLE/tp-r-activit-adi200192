package fr.uga.miage.m1.client.crypto.component;

import fr.uga.miage.m1.client.crypto.response.CryptoFluctuation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Random;

@Component
@RequiredArgsConstructor
@Slf4j
public class ClientCryptoComponent {
    private double price = 1D;

    public CryptoFluctuation getLastCryptoFluctuation() {
        return CryptoFluctuation.builder()
            .currentPrice(getLastPrice())
            .build();
    }

    public Mono<CryptoFluctuation> getReactiveLastCryptoFluctuation() {
        return getReactiveLastPrice(this.price).map(price ->
            CryptoFluctuation.builder()
                .currentPrice(price)
                .build()
        );
    }

    private double getLastPrice() {
        double mu = 0.0005;            // Rendement moyen (drift)
        double sigma = 0.02;           // Volatilité
        int days = 30;                 // Durée de la simulation en jours
        int stepsPerDay = 1;           // Nombre de pas par jour (peut être ajusté)
        double dt = 1.0 / stepsPerDay; // Intervalle de temps

        // Générateur aléatoire
        Random random = new Random();
        double gaussian = random.nextGaussian();

        price *= Math.exp((mu - 0.5 * sigma * sigma) * dt + sigma * Math.sqrt(dt) * gaussian);
        return this.price;
    }

    private Mono<Double> getReactiveLastPrice(double interval) {
        return Mono.just(interval).map(price -> {
            double mu = 0.5;            // Rendement moyen (drift)
            double sigma = 0.2;           // Volatilité
            int stepsPerSecond = 2;           // Nombre de pas par jour (peut être ajusté)
            double dt = 1.0 / stepsPerSecond; // Intervalle de temps

            // Générateur aléatoire
            Random random = new Random();
            double gaussian = random.nextGaussian();

            price *= Math.exp((mu - 0.5 * sigma * sigma) * dt + sigma * Math.sqrt(dt) * gaussian);

            if(gaussian >0.8)  price += 1;
            return price;
        });
    }

    public Flux<CryptoFluctuation> getCryptoFluctuation() {
        return Flux
            .interval(Duration.ofSeconds(1))
            .flatMap(price -> getReactiveLastCryptoFluctuation());
    }
}
