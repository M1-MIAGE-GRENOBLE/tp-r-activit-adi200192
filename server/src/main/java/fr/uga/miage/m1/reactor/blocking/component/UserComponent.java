package fr.uga.miage.m1.reactor.blocking.component;

import fr.uga.miage.m1.reactor.blocking.repository.UserRepository;
import fr.uga.miage.m1.reactor.entity.UserEntity;
import fr.uga.miage.m1.reactor.exception.technic.UserEntityNotFoundException;
import fr.uga.miage.m1.reactor.mapper.UserMapper;
import fr.uga.miage.m1.reactor.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserComponent {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public User create(String pseudo) {
        UserEntity entity = userRepository.create(pseudo);
        return userMapper.toUser(entity);
    }

    public User getByPseudo(String pseudo) throws UserEntityNotFoundException {
        return userMapper.toUser(userRepository.getByPseudo(pseudo));
    }

    public Collection<User> getAll() {
        return userRepository.getAll()
                .stream()
                .map(userMapper::toUser)
                .collect(Collectors.toSet());
    }
}

