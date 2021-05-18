package ua.comsys.kpi.snailboard.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ua.comsys.kpi.snailboard.user.dto.UserInfoDto;
import ua.comsys.kpi.snailboard.user.facade.UserFacade;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserFacade userFacade;

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

    @Secured("ROLE_USER")
    @GetMapping("/usersInfo/{username}")
    public List<UserInfoDto> getUsersByUsername(@PathVariable(required = false) String username,
                                                @RequestParam(required = false) Integer limit) {
        if (limit != null) {
            return userFacade.getUsersByUsername(username, limit);
        } else {
            return userFacade.getUsersByUsername(username);
        }
    }

    @Secured("ROLE_USER")
    @GetMapping("/usersInfo")
    public List<UserInfoDto> getAllUsers() {
        return userFacade.getAllUsersInfo();
    }
}
