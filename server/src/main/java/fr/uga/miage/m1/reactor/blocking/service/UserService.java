package fr.uga.miage.m1.reactor.blocking.service;

import fr.uga.miage.m1.reactor.blocking.component.UserComponent;
import fr.uga.miage.m1.reactor.exception.domain.UserNotFoundException;
import fr.uga.miage.m1.reactor.exception.technic.UserEntityNotFoundException;
import fr.uga.miage.m1.reactor.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class UserService {
private final UserComponent userComponent;

    public User create(String pseudo) {
        return userComponent.create(pseudo);
    }

    public User getByPseudo(String pseudo) {
        try {
            return userComponent.getByPseudo(pseudo);
        } catch (UserEntityNotFoundException e) {
            throw new UserNotFoundException(e.getMessage());
        }
    }

    public Collection<User> getAll() {
        return userComponent.getAll();
    }
}

