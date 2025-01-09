package fr.uga.miage.m1.reactor.reactive.component;

import fr.uga.miage.m1.reactor.mapper.UserMapper;
import fr.uga.miage.m1.reactor.model.User;
import fr.uga.miage.m1.reactor.reactive.repository.ReactiveUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Component
@RequiredArgsConstructor
public class ReactiveUserComponent {
    private final ReactiveUserRepository reactiveUserRepository;
    private final UserMapper userMapper;

    public Mono<User> createUser(String pseudo) {
        return reactiveUserRepository.create(pseudo)
                .map(userMapper::toUser);
    }

    public Mono<User> getByPseudo(String pseudo) {
        return reactiveUserRepository.getByPseudo(pseudo)
                .map(userMapper::toUser);
    }

    public Flux<User> getAll() {
        return reactiveUserRepository.getAll()
                .map(userMapper::toUser);
    }
}
