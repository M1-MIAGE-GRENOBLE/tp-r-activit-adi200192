package fr.uga.miage.m1.reactor.blocking.controller;

import fr.uga.miage.m1.reactor.blocking.repository.CryptoRepository;
import fr.uga.miage.m1.reactor.entity.CryptoEntity;
import fr.uga.miage.m1.reactor.model.CryptoType;
import fr.uga.miage.m1.request.CryptoTypeRequest;
import io.netty.handler.codec.http.HttpMethod;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.model.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.mockserver.model.HttpRequest.request;

@AutoConfigureWebTestClient
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CryptoControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private CryptoRepository cryptoRepository;

    private static ClientAndServer mockServer;


    @BeforeAll
    static void setup() {
        mockServer = ClientAndServer.startClientAndServer();
    }

    @AfterAll
    static void teardDownAll() {
        mockServer.stop();
    }

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("client.url", () -> "http://localhost:" + mockServer.getLocalPort());
    }


    @AfterEach
    public void tearDown() {
        mockServer.reset();
    }

    @Test
    void getAll() {
        webTestClient.get().uri("/crypto/all")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .json("""
                                [
                                  {
                                    "type": "ETHEREUM",
                                    "name": "Ethereum ETH",
                                    "safetyIndex": 1,
                                    "externeSource": true
                                  },
                                  {
                                    "type": "BITCOIN",
                                    "name": "Bitcoin BTC",
                                    "safetyIndex": 1,
                                    "externeSource": true
                                  },
                                  {
                                    "type": "DODGE_COIN",
                                    "name": "Dodge coin DC",
                                    "safetyIndex": 6,
                                    "externeSource": true
                                  },
                                  {
                                    "type": "SOLANA",
                                    "name": "Solana SLN",
                                    "safetyIndex": 1,
                                    "externeSource": true
                                  },
                                  {
                                    "type": "PEPE",
                                    "name": "Pepe",
                                    "safetyIndex": 8,
                                    "externeSource": false
                                  },
                                  {
                                    "type": "CARDANO",
                                    "name": "Cardano CRD",
                                    "safetyIndex": 1,
                                    "externeSource": false
                                  }
                                ]
                        """);
    }

    @EnumSource(CryptoTypeRequest.class)
    @ParameterizedTest
    void getCrypto(CryptoTypeRequest request) {
        CryptoEntity entity = cryptoRepository.getCrypto(CryptoType.valueOf(request.name()));
        webTestClient.get().uri("/crypto/" + request)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .jsonPath("$.name").isEqualTo(entity.getName())
                .jsonPath("$.type").isEqualTo(entity.getType())
                .jsonPath("$.safetyIndex").isEqualTo(entity.getSafetyIndex())
                .jsonPath("$.externeSource").isEqualTo(entity.isExterneSource());
    }

    @Test
    void getCryptoBadRequest() {
        webTestClient.get().uri("/crypto/UNKNOWN")
                .exchange()
                .expectStatus()
                .isBadRequest();
    }


    @EnumSource(value = CryptoTypeRequest.class, mode = EnumSource.Mode.INCLUDE, names = {
            "PEPE",
            "CARDANO"})
    @ParameterizedTest
    void getCryptoInterneFluctuation(CryptoTypeRequest type) {
        webTestClient.get()
                .uri("/crypto/" + type + "/fluctuation/all")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .jsonPath("$.size()").isEqualTo(1)
                .jsonPath("$.[0].currentPrice")
                .isNumber();
    }

    @EnumSource(value = CryptoTypeRequest.class, mode = EnumSource.Mode.EXCLUDE, names = {
            "PEPE",
            "CARDANO"}
    )
    @ParameterizedTest
    void getCryptoExterneFluctuation(CryptoTypeRequest type) {
        mockServer
                .when(
                        request()
                                .withPath("/client/crypto/fluctuation/")
                                .withMethod(HttpMethod.GET.name())
                                .withBody("""
                                        {
                                         "clientCryptoType": "BITCOIN"
                                        }
                                        """.trim())
                ).respond(
                        HttpResponse
                                .response()
                                .withStatusCode(HttpStatus.OK.value())
                                .withContentType(org.mockserver.model.MediaType.APPLICATION_JSON)
                                .withBody("""
                                        [
                                           {
                                              "currentPrice" : 1.09
                                           }
                                         ]
                                        """.trim())
                );

        webTestClient.get()
                .uri("/crypto/" + type + "/fluctuation/all")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .jsonPath("$.currentPrice").isNumber();

    }

}
