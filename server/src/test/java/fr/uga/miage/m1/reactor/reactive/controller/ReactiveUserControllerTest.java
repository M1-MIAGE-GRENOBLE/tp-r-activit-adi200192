package fr.uga.miage.m1.reactor.reactive.controller;

import fr.uga.miage.m1.reactor.entity.UserEntity;
import fr.uga.miage.m1.reactor.reactive.repository.ReactiveUserRepository;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.test.StepVerifier;

import java.util.stream.Stream;

import static reactor.core.publisher.Mono.when;

@AutoConfigureWebTestClient
@WebFluxTest(ReactiveUserControllerTest.class)
class ReactiveUserControllerTest {

    @Autowired
    private WebTestClient webClient;

    @Autowired
    private ReactiveUserRepository userRepository;


    @SneakyThrows
    @BeforeEach
    void init() {
        Stream.of("test1", "test2", "test3")
                .forEach(s -> when(userRepository.create(s)).dematerialize().block());
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    void getAllUsers() {
        webClient.get()
                .uri("/reactive/users/all")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .json(
                        """
                                [
                                  {
                                    "pseudo" :"test1"
                                  },
                                  {
                                    "pseudo" :"test2"
                                  },
                                  {
                                    "pseudo" :"test3"
                                  }
                                ]
                                """
                );
    }

    @Test
    void getUserById() {
        webClient.get().uri("/reactive/users/test3")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .json("""
                        {
                           "pseudo" :"test3"
                        }
                        """);
    }


    @Test
    void getUserByPseudoNotFound() {
        webClient.get().uri("/reactive/users/unknown")
                .exchange()
                .expectStatus()
                .isNotFound();
    }


    @Test
    void createUser() {
        webClient.post()
                .uri("/reactive/users/create/toto")
                .contentType(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isCreated()
                .expectBody()
                .json("""
                        {
                          "pseudo" :"toto"
                        }
                        """);

        StepVerifier.create(userRepository.getByPseudo("toto"))
                .expectNext(UserEntity.builder().pseudo("toto").build())
                .verifyComplete();
    }

}
