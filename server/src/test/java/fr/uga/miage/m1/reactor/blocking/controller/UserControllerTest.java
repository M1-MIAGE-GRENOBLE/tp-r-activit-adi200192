package fr.uga.miage.m1.reactor.blocking.controller;


import fr.uga.miage.m1.reactor.blocking.repository.UserRepository;
import fr.uga.miage.m1.reactor.entity.UserEntity;
import fr.uga.miage.m1.reactor.exception.technic.UserEntityNotFoundException;
import lombok.SneakyThrows;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.stream.Stream;

@AutoConfigureWebTestClient
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserControllerTest {
    @Autowired
    private WebTestClient webClient;

    @Autowired
    private UserRepository userRepository;


    @SneakyThrows
    @BeforeEach
    void init() {
        Stream.of("test1", "test2", "test3")
                .forEach(userRepository::create);
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }


    @Test
    void getAllUsers() {

        webClient.get()
                .uri("/users/all")
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
        webClient.get().uri("/users/test3")
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
        webClient.get().uri("/users/unknown")
                .exchange()
                .expectStatus()
                .isNotFound();
    }


    @Test
    void createUser() throws UserEntityNotFoundException {
        webClient.post()
                .uri("/users/create/toto")
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

        Assertions.assertThat(userRepository.getAll()).hasSize(4);
        Assertions.assertThat(userRepository.getByPseudo("toto")).isEqualTo(UserEntity.builder().pseudo("toto").build());
    }


}
