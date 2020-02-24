package service.impl;

import dao.UserDao;
import domain.User;
import entity.UserEntity;
import mapper.Mapper;
import service.UserService;
import service.encryptor.Encryptor;
import service.validator.DuplicateException;
import service.validator.Validator;

import java.util.NoSuchElementException;

public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final Mapper<User, UserEntity> mapper;
    private final Encryptor encryptor;
    private final Validator<User> validator;

    public UserServiceImpl(UserDao userDao, Mapper<User, UserEntity> mapper, Encryptor encryptor, Validator<User> validator) {
        this.userDao = userDao;
        this.mapper = mapper;
        this.encryptor = encryptor;
        this.validator = validator;
    }

    @Override
    public User login(String email, String password) {
        return userDao.findByEmail(email)
                .map(mapper::mapEntityToDomain)
                .filter(user -> encryptor.checkPassword(password.toCharArray(), user.getPassword()))
                .orElseThrow(() -> {throw new NoSuchElementException("User not found");
        });
    }

    @Override
    public User findById(Integer id) {
        return userDao.findById(id)
                .map(mapper::mapEntityToDomain)
                .orElseThrow();
    }

    @Override
    public void register(User user) {
        validator.validate(user);

        userDao.findByEmail(user.getEmail())
                .ifPresent( s -> {throw new DuplicateException("User already present");});

        user.setPassword(encryptor.hash(user.getPassword().toCharArray()));

        userDao.save(mapper.mapDomainToEntity(user));
    }
}
