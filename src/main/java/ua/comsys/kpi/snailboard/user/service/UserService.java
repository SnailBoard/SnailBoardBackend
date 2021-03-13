package ua.comsys.kpi.snailboard.user.service;

import ua.comsys.kpi.snailboard.user.model.User;

import java.util.Optional;

public interface UserService {
    public User createUser(User userEntity);
    public Optional<User> findByEmail(String login);
    public Optional<User> findByEmailAndPassword(String login, String password);
}
