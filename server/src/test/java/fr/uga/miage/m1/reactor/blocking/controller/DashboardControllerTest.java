package fr.uga.miage.m1.reactor.blocking.controller;

import fr.uga.miage.m1.client.crypto.request.ClientCryptoType;
import fr.uga.miage.m1.reactor.blocking.repository.DashboardRepository;
import fr.uga.miage.m1.reactor.exception.technic.DashboardEntityIsAlreadyDefinedException;
import fr.uga.miage.m1.reactor.model.CryptoType;
import fr.uga.miage.m1.reactor.model.DashboardToCreate;
import fr.uga.miage.m1.request.CryptoTypeRequest;
import fr.uga.miage.m1.request.DashboardCreationRequest;
import io.netty.handler.codec.http.HttpMethod;
import org.junit.jupiter.api.*;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.model.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;

import static org.mockserver.model.HttpRequest.request;

@AutoConfigureWebTestClient
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DashboardControllerTest {

    @Autowired
    private WebTestClient webClient;

    @Autowired
    private DashboardRepository dashboardRepository;

    private static ClientAndServer mockServer;

    @BeforeAll
    static void setup() {
        mockServer = ClientAndServer.startClientAndServer();
    }

    @AfterAll
    static void teardDownAll() {

    }

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("client.url", () -> "http://localhost:" + mockServer.getLocalPort());
    }


    @BeforeEach
    void setUp() throws DashboardEntityIsAlreadyDefinedException {
        mockServer.reset();
        dashboardRepository.create(DashboardToCreate.builder()
                .cryptos(List.of(CryptoType.SOLANA,
                        CryptoType.CARDANO
                )).build()
        );
    }

    @AfterEach
    void tearDown() {
        mockServer.reset();
        dashboardRepository.deleteDashboard();
    }

    @Test
    void getDashboard() {
        webClient.get().uri("/dashboard/")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .json("""
                        {
                          "SOLANA": {
                            "type": "SOLANA",
                            "name": "Solana SLN",
                            "safetyIndex": 1,
                            "externeSource": true
                          },
                          "CARDANO": {
                            "type": "CARDANO",
                            "name": "Cardano CRD",
                            "safetyIndex": 1,
                            "externeSource": false
                          }
                        }
                        """);
    }

    @Test
    void getDashboardNotDefined() {
        dashboardRepository.deleteDashboard();
        webClient.get().uri("/dashboard/")
                .exchange()
                .expectStatus()
                .isNotFound();

    }


    @Test
    void createDashboard() {
        dashboardRepository.deleteDashboard();
        webClient
                .post()
                .uri("/dashboard/")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue((DashboardCreationRequest.builder().cryptos(List.of(
                        CryptoTypeRequest.CARDANO,
                        CryptoTypeRequest.SOLANA
                )).build()))
                .exchange()
                .expectStatus()
                .isCreated()
                .expectBody()
                .json("""
                        {
                          "SOLANA": {
                            "type": "SOLANA",
                            "name": "Solana SLN",
                            "safetyIndex": 1,
                            "externeSource": true
                          },
                          "CARDANO": {
                            "type": "CARDANO",
                            "name": "Cardano CRD",
                            "safetyIndex": 1,
                            "externeSource": false
                          }
                        }
                        """);
    }

    @Test
    void createDashboardAlreadyDefined() {
        webClient
                .post()
                .uri("/dashboard/")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(DashboardCreationRequest.builder().cryptos(List.of(
                        CryptoTypeRequest.CARDANO,
                        CryptoTypeRequest.SOLANA
                )).build())
                .exchange()
                .expectStatus()
                .isEqualTo(HttpStatus.CONFLICT);
    }


    @Test
    void getLastDashboardFluctuation() {
        mockServer
                .when(
                        request()
                                .withPath("/client/crypto/fluctuation/last")
                                .withMethod(HttpMethod.GET.name())
                                .withBody(String.format("{'clientCryptoType': '%s'}", ClientCryptoType.SOLANA.name()))
                ).respond(
                        HttpResponse
                                .response()
                                .withStatusCode(HttpStatus.OK.value())
                                .withContentType(org.mockserver.model.MediaType.APPLICATION_JSON)
                                .withBody("""
                                        
                                           {
                                              "currentPrice" : 1.09
                                           }
                                        
                                        """.trim())
                );

        webClient.get().uri("/dashboard/fluctuation/last")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .json("""
                        {
                          "SOLANA" : {
                            "currentPrice": 1.008
                          },
                          "CARDANO" : {
                            "currentPrice": 1.009
                          }
                        
                        }
                        """);

    }


    @Test
    void getAllDashboardFluctuation() {
        mockServer
                .when(
                        request()
                                .withPath("/client/crypto/fluctuation/")
                                .withMethod(HttpMethod.GET.name())
                                .withBody(String.format("{'clientCryptoType': '%s'}", ClientCryptoType.SOLANA.name()))
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
        webClient.get().uri("/dashboard/fluctuation/all")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .json("""
                        {
                          "SOLANA": [
                            {
                              "currentPrice": 1.008
                            },
                            {
                              "currentPrice": 4.345
                            }
                          ],
                          "CARDANO": [
                            {
                              "currentPrice": 1.009
                            },
                            {
                              "currentPrice": 1.001
                            }
                          ]
                        }
                        """);
    }

}
