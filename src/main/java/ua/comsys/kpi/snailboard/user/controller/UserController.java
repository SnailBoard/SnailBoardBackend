package ua.comsys.kpi.snailboard.user.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

//todo: TESTING CLASS TO BE REMOVED

@RestController
public class UserController {

    @Secured("ROLE_ADMIN")
    @GetMapping("/admin/get")
    public String getAdmin() {
        return "Hi admin";
    }

    @Secured("ROLE_USER")
    @GetMapping("/user/get")
    public String getUser() {
        return "Hi user";
    }
}
