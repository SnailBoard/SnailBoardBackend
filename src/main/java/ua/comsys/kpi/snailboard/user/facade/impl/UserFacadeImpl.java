package ua.comsys.kpi.snailboard.user.facade.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.comsys.kpi.snailboard.security.jwt.JWTProvider;
import ua.comsys.kpi.snailboard.user.dto.UserInfoDto;
import ua.comsys.kpi.snailboard.user.exception.UserNotFoundException;
import ua.comsys.kpi.snailboard.user.facade.UserFacade;
import ua.comsys.kpi.snailboard.user.model.User;
import ua.comsys.kpi.snailboard.user.service.UserService;
import ua.comsys.kpi.snailboard.utils.Converter;

import java.util.List;
import java.util.stream.Collectors;


@Component
public class UserFacadeImpl implements UserFacade {

    @Autowired
    private UserService userService;

    @Autowired
    private JWTProvider jwtProvider;

    @Autowired
    Converter<User, UserInfoDto> userUserInfoDtoConverter;

    @Override
    public UserInfoDto getCurrentUserInfo() {
        return userService.getUserInfoByEmail(jwtProvider.getCurrentUserEmail());
    }

    @Override
    public User getCurrentUserModel() {
        return userService.findByEmail(jwtProvider.getCurrentUserEmail()).orElseThrow(UserNotFoundException::new);
    }

    @Override
    public List<UserInfoDto> getUsersByUsername(String username) {
        List<User> users = userService.getUsersByUsername(username);
        return users.stream().map(userUserInfoDtoConverter::convert).collect(Collectors.toList());
    }

    @Override
    public List<UserInfoDto> getUsersByUsername(String username, Integer limit) {
        List<User> users = userService.getUsersByUsernameWithLimit(username, limit);
        return users.stream().map(userUserInfoDtoConverter::convert).collect(Collectors.toList());
    }
}
