package ua.comsys.kpi.snailboard.user.facade;

import ua.comsys.kpi.snailboard.user.dto.UserInfoDto;
import ua.comsys.kpi.snailboard.user.model.User;

import java.util.List;

public interface UserFacade {
    UserInfoDto getCurrentUserInfo();

    User getCurrentUserModel();

    List<UserInfoDto> getUsersByUsername(String username);

    List<UserInfoDto> getUsersByUsername(String username, Integer limit);

    List<UserInfoDto> getAllUsersInfo();
}
