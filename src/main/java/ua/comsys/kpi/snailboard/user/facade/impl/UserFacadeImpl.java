package ua.comsys.kpi.snailboard.user.facade.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ua.comsys.kpi.snailboard.user.dto.UserInfoDto;
import ua.comsys.kpi.snailboard.user.facade.UserFacade;
import ua.comsys.kpi.snailboard.user.service.UserService;

import static ua.comsys.kpi.snailboard.security.jwt.JWTProvider.getCurrentUserEmail;

@RequiredArgsConstructor
@Component
public class UserFacadeImpl implements UserFacade {

    private final UserService userService;

    @Override
    public UserInfoDto getCurrentUserInfo() {
        return userService.getUserInfoByEmail(getCurrentUserEmail());
    }
}
