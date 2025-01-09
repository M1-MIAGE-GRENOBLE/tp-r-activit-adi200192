package fr.uga.miage.m1.reactor.blocking.repository;

import fr.uga.miage.m1.reactor.entity.UserEntity;
import fr.uga.miage.m1.reactor.exception.technic.UserEntityNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashSet;

@Repository
public class UserRepository {
    private final Collection<UserEntity> users = new HashSet<>();

    public UserEntity create(String pseudo) {
        UserEntity entity = UserEntity.builder()
                .pseudo(pseudo)
                .build();
        users.add(entity);
        return entity;
    }

    public UserEntity getByPseudo(String pseudo) throws UserEntityNotFoundException {
        return users.stream().filter(
                        userEntity -> userEntity.getPseudo().equals(pseudo)
                ).findFirst()
                .orElseThrow(() -> new UserEntityNotFoundException(String.format("User %s not found", pseudo)));
    }

    public Collection<UserEntity> getAll() {
        return users;
    }


    public void deleteAll(){
        users.clear();
    }
}
