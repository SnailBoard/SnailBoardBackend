package ua.comsys.kpi.snailboard.user.service;

import ua.comsys.kpi.snailboard.user.dto.UserInfoDto;
import ua.comsys.kpi.snailboard.user.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User createUser(User userEntity);

    Optional<User> findByEmail(String login);

    Optional<User> findByEmailAndPassword(String login, String password);

    UserInfoDto getUserInfoByEmail(String email);

    List<User> getUsersByUsername(String username);

    List<User> getUsersByUsernameWithLimit(String username, Integer limit);

    List<User> getAllUsers();
}
