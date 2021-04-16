package ua.comsys.kpi.snailboard.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.comsys.kpi.snailboard.user.dto.UserInfoDto;
import ua.comsys.kpi.snailboard.user.facade.UserFacade;

//todo: TESTING CLASS TO BE REMOVED

@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserFacade userFacade;

    @Secured("ROLE_ADMIN")
    @GetMapping("/admin/get")
    public String getAdmin() {
        return "Hi admin";
    }

    @Secured("ROLE_USER")
    @GetMapping("/user")
    public UserInfoDto getUser() {
        return userFacade.getCurrentUserInfo();
    }


}
