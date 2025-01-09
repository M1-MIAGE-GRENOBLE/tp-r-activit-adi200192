package fr.uga.miage.m1.reactor.reactive.repository;

import fr.uga.miage.m1.reactor.entity.UserEntity;
import fr.uga.miage.m1.reactor.exception.technic.UserEntityNotFoundException;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.HashSet;

@Repository
public class ReactiveUserRepository {
    private final Collection<UserEntity> users = new HashSet<>();

    public Mono<UserEntity> create(String pseudo) {
        return Mono.just(UserEntity.builder()
                .pseudo(pseudo)
                .build()
        ).doOnNext(users::add);
    }

    public Mono<UserEntity> getByPseudo(String pseudo) {
        return Flux.fromStream(users.stream())
                .filter(userEntity -> userEntity.getPseudo().equals(pseudo))
                .switchIfEmpty(Mono.error(
                        new UserEntityNotFoundException(String.format("User [%s] not found", pseudo))
                ))
                .single();
    }

    public Flux<UserEntity> getAll() {
        return Flux.fromStream(users.stream());
    }

    public void deleteAll() {
        users.clear();
    }
}
