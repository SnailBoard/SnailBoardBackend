package ua.comsys.kpi.snailboard.user.facade.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ua.comsys.kpi.snailboard.security.jwt.JWTProvider;
import ua.comsys.kpi.snailboard.token.access.dto.AccessTokenDTO;
import ua.comsys.kpi.snailboard.token.refresh.dto.RefreshTokenDTO;
import ua.comsys.kpi.snailboard.token.refresh.service.RefreshTokenService;
import ua.comsys.kpi.snailboard.user.dto.AuthRequest;
import ua.comsys.kpi.snailboard.user.dto.AuthResponse;
import ua.comsys.kpi.snailboard.user.dto.RegistrationRequest;
import ua.comsys.kpi.snailboard.user.exception.UserNotFoundException;
import ua.comsys.kpi.snailboard.user.facade.AuthFacade;
import ua.comsys.kpi.snailboard.user.model.User;
import ua.comsys.kpi.snailboard.user.service.UserService;

import java.util.ArrayList;
import java.util.HashSet;

@Component
public class AuthFacadeImpl implements AuthFacade {

    @Autowired
    private UserService userService;

    @Autowired
    private JWTProvider jwtProvider;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void createUser(RegistrationRequest registrationRequest) {
        User user = User.builder()
                .password(registrationRequest.getPassword())
                .email(registrationRequest.getEmail())
                .firstName(registrationRequest.getFirstName())
                .username(registrationRequest.getUsername())
                .imageId(registrationRequest.getImage())
                .build();
        userService.createUser(user);
    }

    @Override
    public AuthResponse authUser(AuthRequest request) {
        User user = userService.findByEmailAndPassword(request.getEmail(), request.getPassword())
                .orElseThrow(UserNotFoundException::new);
        AccessTokenDTO accessToken = jwtProvider.generateAccessToken(user.getEmail());
        RefreshTokenDTO refreshToken = jwtProvider.generateRefreshToken(user.getEmail());
        refreshTokenService.createOrUpdateRefreshToken(user.getEmail(), refreshToken);
        return new AuthResponse(accessToken.getToken(), refreshToken.getToken());
    }
}
