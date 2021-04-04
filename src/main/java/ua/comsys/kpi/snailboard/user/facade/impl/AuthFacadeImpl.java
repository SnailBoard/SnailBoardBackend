package ua.comsys.kpi.snailboard.user.facade.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ua.comsys.kpi.snailboard.security.jwt.JWTProvider;
import ua.comsys.kpi.snailboard.token.refresh.service.RefreshTokenService;
import ua.comsys.kpi.snailboard.user.dto.AuthRequest;
import ua.comsys.kpi.snailboard.user.dto.AuthResponse;
import ua.comsys.kpi.snailboard.user.dto.RegistrationRequest;
import ua.comsys.kpi.snailboard.user.exception.UserNotFoundException;
import ua.comsys.kpi.snailboard.user.facade.AuthFacade;
import ua.comsys.kpi.snailboard.user.model.User;
import ua.comsys.kpi.snailboard.user.service.UserService;

import java.util.ArrayList;

@Component
public class AuthFacadeImpl implements AuthFacade {

    private final UserService userService;
    private final JWTProvider jwtProvider;
    private final RefreshTokenService refreshTokenService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthFacadeImpl(UserService userService, JWTProvider jwtProvider, RefreshTokenService refreshTokenService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.jwtProvider = jwtProvider;
        this.refreshTokenService = refreshTokenService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void createUser(RegistrationRequest registrationRequest) {
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
    }

    @Override
    public AuthResponse authUser(AuthRequest request) {
        User user = userService.findByEmailAndPassword(request.getEmail(), request.getPassword())
                .orElseThrow(UserNotFoundException::new);
        String accessToken = jwtProvider.generateAccessToken(user.getEmail());
        String refreshToken = jwtProvider.generateRefreshToken(user.getEmail());
        refreshTokenService.createOrUpdateRefreshToken(user.getEmail(), passwordEncoder.encode(refreshToken));
        return new AuthResponse(accessToken, refreshToken);
    }
}
