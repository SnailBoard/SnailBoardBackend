package ua.comsys.kpi.snailboard.user.facade;

import ua.comsys.kpi.snailboard.user.dto.UserInfoDto;

import java.util.List;

public interface UserFacade {
    UserInfoDto getCurrentUserInfo();
    List<UserInfoDto> getUsersByUsername(String username);
    List<UserInfoDto> getUsersByUsername(String username, Integer limit);
}
