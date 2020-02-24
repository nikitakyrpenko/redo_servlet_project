package dao;

import entity.UserEntity;

import java.util.Optional;

public interface UserDao extends CrudDao<UserEntity>{

    Optional<UserEntity> findByEmail(String email);

}
