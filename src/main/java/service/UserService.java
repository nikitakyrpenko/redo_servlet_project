package service;

import domain.User;

public interface UserService {

    User login(String email, String password);

    User findById(Integer id);

    void register(User user);

}
