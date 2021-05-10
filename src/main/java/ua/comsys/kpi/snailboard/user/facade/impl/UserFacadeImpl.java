package ua.comsys.kpi.snailboard.user.facade.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.comsys.kpi.snailboard.security.jwt.JWTProvider;
import ua.comsys.kpi.snailboard.user.dto.UserInfoDto;
import ua.comsys.kpi.snailboard.user.facade.UserFacade;
import ua.comsys.kpi.snailboard.user.model.User;
import ua.comsys.kpi.snailboard.user.service.UserService;

import java.util.List;
import java.util.stream.Collectors;


@Component
public class UserFacadeImpl implements UserFacade {

    @Autowired
    private UserService userService;

    @Autowired
    private JWTProvider jwtProvider;

    @Override
    public UserInfoDto getCurrentUserInfo() {
        return userService.getUserInfoByEmail(jwtProvider.getCurrentUserEmail());
    }

    @Override
    public List<UserInfoDto> getUsersByUsername(String username) {
        List<User> users = userService.getUsersByUsername(username);
        return users.stream().map(UserInfoDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public List<UserInfoDto> getUsersByUsername(String username, Integer limit) {
        List<User> users = userService.getUsersByUsernameWithLimit(username, limit);
        return users.stream().map(UserInfoDto::fromEntity).collect(Collectors.toList());
    }
}
