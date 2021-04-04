package ua.comsys.kpi.snailboard.user.controller;

import com.fasterxml.jackson.databind.node.TextNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ua.comsys.kpi.snailboard.token.refresh.facade.RefreshTokenFacade;
import ua.comsys.kpi.snailboard.user.dto.AuthRequest;
import ua.comsys.kpi.snailboard.user.dto.AuthResponse;
import ua.comsys.kpi.snailboard.user.dto.RefreshTokenRequest;
import ua.comsys.kpi.snailboard.user.dto.RegistrationRequest;
import ua.comsys.kpi.snailboard.user.facade.AuthFacade;

@RestController
public class AuthController {

    @Autowired
    AuthFacade authFacade;

    @Autowired
    RefreshTokenFacade refreshTokenFacade;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void registerUser(@RequestBody RegistrationRequest registrationRequest) {
        authFacade.createUser(registrationRequest);
    }

    @PostMapping("/auth")
    public AuthResponse auth(@RequestBody AuthRequest authRequest) {
        return authFacade.authUser(authRequest);
    }

    @PostMapping("/refresh")
    public AuthResponse refresh(@RequestBody RefreshTokenRequest refreshToken) {
        return refreshTokenFacade.refreshToken(refreshToken.getRefreshToken());
    }
}
