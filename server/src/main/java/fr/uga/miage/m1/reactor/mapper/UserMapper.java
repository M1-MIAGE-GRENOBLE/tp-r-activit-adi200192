package fr.uga.miage.m1.reactor.mapper;

import fr.uga.miage.m1.reactor.entity.UserEntity;
import fr.uga.miage.m1.reactor.model.User;
import fr.uga.miage.m1.response.UserResponse;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {

    UserResponse toResponse(User user);

    UserEntity toEntity(User user);

    User toUser(UserEntity entity);
}
