package fr.uga.miage.m1.reactor.reactive.service;

import fr.uga.miage.m1.reactor.exception.domain.UserCreationException;
import fr.uga.miage.m1.reactor.exception.domain.UserNotFoundException;
import fr.uga.miage.m1.reactor.exception.technic.UserEntityCreationException;
import fr.uga.miage.m1.reactor.exception.technic.UserEntityNotFoundException;
import fr.uga.miage.m1.reactor.model.User;
import fr.uga.miage.m1.reactor.reactive.component.ReactiveUserComponent;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ReactiveUserService {
    private final ReactiveUserComponent reactiveUserComponent;

    @SneakyThrows(UserCreationException.class)
    public Mono<User> createUser(String pseudo){
        return reactiveUserComponent.createUser(pseudo)
                .onErrorMap(UserEntityCreationException.class, e -> new UserCreationException(e.getMessage()));
    }

    public Mono<User> getByPseudo(String pseudo) {
        return reactiveUserComponent.getByPseudo(pseudo)
                .onErrorMap(UserEntityNotFoundException.class, e -> new UserNotFoundException(e.getMessage()));
    }

    public Flux<User> getAll() {
        return reactiveUserComponent.getAll();
    }
}
