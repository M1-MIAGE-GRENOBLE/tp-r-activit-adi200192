package fr.uga.miage.m1.reactor.reactive.controller;

import fr.uga.miage.m1.client.crypto.request.ClientCryptoType;
import fr.uga.miage.m1.reactor.model.CryptoType;
import fr.uga.miage.m1.reactor.model.DashboardToCreate;
import fr.uga.miage.m1.reactor.reactive.repository.ReactiveDashboardRepository;
import fr.uga.miage.m1.request.CryptoTypeRequest;
import fr.uga.miage.m1.response.CryptoFluctuationResponse;
import fr.uga.miage.m1.response.CryptoTypeResponse;
import io.netty.handler.codec.http.HttpMethod;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.model.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.List;
import java.util.Map;

import static org.mockserver.model.HttpRequest.request;

@AutoConfigureWebTestClient
@WebFluxTest(ReactiveDashboardController.class)
class ReactiveDashboardControllerTest {

    @Autowired
    private WebTestClient webClient;

    @Autowired
    private ReactiveDashboardRepository reactiveDashboardRepository;

    private static ClientAndServer mockServer;

    @BeforeAll
    static void setup() {
        mockServer = ClientAndServer.startClientAndServer();
    }

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("client.url", () -> "http://localhost:" + mockServer.getLocalPort());
    }

    @BeforeEach
    void setUp() {
        mockServer.reset();
        reactiveDashboardRepository.create(DashboardToCreate.builder()
                .cryptos(List.of(CryptoType.SOLANA,
                        CryptoType.CARDANO
                )).build()
        ).block();
    }

    @AfterEach
    void tearDown() {
        mockServer.stop();
        reactiveDashboardRepository.deleteDashboard().dematerialize().block();
    }

    @Test
    void getDashboard() {
        webClient.get().uri("/reactive/dashboard")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .json("""
                        {
                          "SOLANA" : {
                            "name" : "Solana SLN",
                            "type": "SOLANA",
                            "externalSource" : true,
                            "safetyIndex": 1
                          },
                          "CARDANO" : {
                            "name" : "Cardano CRD",
                            "type": "CARDANO",
                            "externalSource" : false,
                            "safetyIndex": 1
                          }
                        
                        }
                        """);
    }

    @Test
    void getDashboardNotDefined() {
        reactiveDashboardRepository.deleteDashboard();
        webClient.get().uri("/reactive/dashboard")
                .exchange()
                .expectStatus()
                .isNotFound();

    }


    @Test
    void createDashboard() {
        reactiveDashboardRepository.deleteDashboard().block();
        webClient
                .post()
                .uri("/reactive/dashboard")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(List.of(
                        CryptoTypeRequest.CARDANO,
                        CryptoTypeRequest.SOLANA
                ))
                .exchange()
                .expectStatus()
                .isCreated()
                .expectBody()
                .json("""
                        {
                          "SOLANA" : {
                            "name" : "Solana SLN",
                            "type": "SOLANA",
                            "externalSource" : true,
                            "safetyIndex": 1
                          },
                          "CARDANO" : {
                            "name" : "Cardano CRD",
                            "type": "CARDANO",
                            "externalSource" : false,
                            "safetyIndex": 1
                          }
                        
                        }
                        """);
    }

    @Test
    void createDashboardAlreadyDefined() {
        webClient
                .post()
                .uri("/reactive/dashboard")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(List.of(
                        CryptoTypeRequest.CARDANO,
                        CryptoTypeRequest.SOLANA
                ))
                .exchange()
                .expectStatus()
                .isEqualTo(HttpStatus.CONFLICT);
    }


    @Test
    void getLastDashboardFluctuation() {
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
        webClient.get().uri("/reactive/dashboard/fluctuation/last")
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
                                .withPath("/reactive/client/crypto/fluctuation/")
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
        webClient.get().uri("/reactive/dashboard/fluctuation/all")
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


    @Test
    void getFluctuation() {
        mockServer
                .when(
                        request()
                                .withPath("/reactive/client/crypto/fluctuation/")
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
        Flux<Map<CryptoTypeResponse, CryptoFluctuationResponse>> responseBody = webClient.get().uri("/reactive/dashboard/fluctuation/")
                .accept(MediaType.TEXT_EVENT_STREAM)
                .exchange()
                .expectStatus()
                .isOk()
                .returnResult(
                        new ParameterizedTypeReference<Map<CryptoTypeResponse, CryptoFluctuationResponse>>() {
                        })
                .getResponseBody();

        StepVerifier.create(responseBody).expectNextCount(1).verifyComplete();


    }

}
