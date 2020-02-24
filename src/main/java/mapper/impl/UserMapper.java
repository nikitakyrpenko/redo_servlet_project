package mapper.impl;

import domain.User;
import entity.UserEntity;
import mapper.Mapper;

public class UserMapper implements Mapper<User, UserEntity> {

    @Override
    public User mapEntityToDomain(UserEntity entity) {
        return User.builder()
                .withId(entity.getId())
                .withName(entity.getName())
                .withSurname(entity.getSurname())
                .withEmail(entity.getEmail())
                .withPassword(entity.getPassword())
                .withTelephone(entity.getTelephone())
                .withRole(entity.getRole())
                .build();
    }

    @Override
    public UserEntity mapDomainToEntity(User domain) {
        return UserEntity.builder()
                .withId(domain.getId())
                .withName(domain.getName())
                .withSurname(domain.getSurname())
                .withEmail(domain.getEmail())
                .withPassword(domain.getPassword())
                .withTelephone(domain.getTelephone())
                .withRole(domain.getRole())
                .build();
    }
}
