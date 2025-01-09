package fr.uga.miage.m1.reactor.blocking.controller;

import fr.uga.miage.m1.endpoint.blocking.UserEndpoints;
import fr.uga.miage.m1.reactor.blocking.service.UserService;
import fr.uga.miage.m1.reactor.mapper.UserMapper;
import fr.uga.miage.m1.reactor.model.User;
import fr.uga.miage.m1.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class UserController implements UserEndpoints {
    private final UserService userService;
    private final UserMapper userMapper;

    @Override
    public UserResponse createUser(String pseudo) {
        User user = userService.create(pseudo);
        return userMapper.toResponse(user);
    }

    @Override
    public UserResponse getByPseudo(String pseudo) {
        User user = userService.getByPseudo(pseudo);
        return userMapper.toResponse(user);
    }

    @Override
    public Collection<UserResponse> getAll() {
        return userService.getAll()
                .stream()
                .map(userMapper::toResponse)
                .collect(Collectors.toSet());
    }
}
