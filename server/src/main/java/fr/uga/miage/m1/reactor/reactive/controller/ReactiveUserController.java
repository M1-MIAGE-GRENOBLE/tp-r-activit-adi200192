package fr.uga.miage.m1.reactor.reactive.controller;

import fr.uga.miage.m1.endpoint.reactive.ReactiveUserEndpoints;
import fr.uga.miage.m1.reactor.mapper.UserMapper;
import fr.uga.miage.m1.reactor.reactive.service.ReactiveUserService;
import fr.uga.miage.m1.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class ReactiveUserController implements ReactiveUserEndpoints {
    private final ReactiveUserService reactiveUserService;
    private final UserMapper userMapper;

    @Override
    public Mono<UserResponse> createUser(String pseudo) {
        return reactiveUserService.createUser(pseudo)
                .map(userMapper::toResponse);
    }

    @Override
    public Mono<UserResponse> getByPseudo(String pseudo) {
        return reactiveUserService.getByPseudo(pseudo)
                .map(userMapper::toResponse);
    }

    @Override
    public Flux<UserResponse> getAll() {
        return reactiveUserService.getAll()
                .map(userMapper::toResponse);
    }
}