package ua.comsys.kpi.snailboard.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ua.comsys.kpi.snailboard.security.jwt.JWTProvider;
import ua.comsys.kpi.snailboard.user.dto.AuthRequest;
import ua.comsys.kpi.snailboard.user.dto.AuthResponse;
import ua.comsys.kpi.snailboard.user.dto.RegistrationRequest;
import ua.comsys.kpi.snailboard.user.model.User;
import ua.comsys.kpi.snailboard.user.service.UserService;

import java.util.ArrayList;

@RestController
public class AuthController {

    @Autowired
    UserService userService;
    @Autowired
    JWTProvider jwtProvider;

    @PostMapping("/register")
    public String registerUser(@RequestBody RegistrationRequest registrationRequest) {
        User user = new User();
        user.setPassword(registrationRequest.getPassword());
        user.setEmail(registrationRequest.getEmail());
        user.setFirstName(registrationRequest.getFirstName());
        user.setUsername(registrationRequest.getUsername());
        user.setNotifications(new ArrayList<>());
        user.setStatistics(new ArrayList<>());
        user.setTeams(new ArrayList<>());
        user.setTickets(new ArrayList<>());
        userService.createUser(user);
        return "OK";
    }

    @PostMapping("/auth")
    public AuthResponse auth(@RequestBody AuthRequest request) {
        User user = userService.findByEmailAndPassword(request.getEmail(), request.getPassword()).get();
        String token = jwtProvider.generateToken(user.getEmail());
        return new AuthResponse(token);
    }
}
