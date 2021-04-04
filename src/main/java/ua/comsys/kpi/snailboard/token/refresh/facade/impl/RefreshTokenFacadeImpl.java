package ua.comsys.kpi.snailboard.token.refresh.facade.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.comsys.kpi.snailboard.security.jwt.JWTProvider;
import ua.comsys.kpi.snailboard.token.refresh.facade.RefreshTokenFacade;
import ua.comsys.kpi.snailboard.token.refresh.service.RefreshTokenService;
import ua.comsys.kpi.snailboard.user.dto.AuthResponse;

import java.util.Date;

@Component
public class RefreshTokenFacadeImpl implements RefreshTokenFacade {

    @Autowired
    private JWTProvider jwtProvider;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @Override
    public AuthResponse refreshToken(String refreshToken) {
        String email = jwtProvider.getLoginFromRefreshToken(refreshToken);
        String accessToken = jwtProvider.generateAccessToken(email);
        Date expiration = jwtProvider.getRefreshExpirationDate(refreshToken);
        String refreshTkn = jwtProvider.generateRefreshToken(expiration, email);
        refreshTokenService.createOrUpdateRefreshToken(email, refreshTkn);
        return new AuthResponse(accessToken, refreshTkn);
    }
}
