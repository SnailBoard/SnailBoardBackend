package ua.comsys.kpi.snailboard.token.refresh.facade.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.comsys.kpi.snailboard.security.jwt.JWTProvider;
import ua.comsys.kpi.snailboard.token.refresh.exception.TokenNotValidException;
import ua.comsys.kpi.snailboard.token.refresh.facade.RefreshTokenFacade;
import ua.comsys.kpi.snailboard.token.refresh.service.RefreshTokenService;
import ua.comsys.kpi.snailboard.user.dto.AuthResponse;

@Component
public class RefreshTokenFacadeImpl implements RefreshTokenFacade {
    private static final Logger LOG = LoggerFactory.getLogger(RefreshTokenFacadeImpl.class);

    @Autowired
    private JWTProvider jwtProvider;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @Override
    public AuthResponse refreshToken(String refreshToken) {
        jwtProvider.validateToken(refreshToken, true);
        String email = jwtProvider.getLoginFromRefreshToken(refreshToken);
        if (!refreshTokenService.validateToken(email, refreshToken)) {
            LOG.warn("Invalid refresh token");
            throw new TokenNotValidException();
        }
        String accessToken = jwtProvider.generateAccessToken(email);
        String refreshTkn = jwtProvider.generateRefreshToken(email);
        refreshTokenService.createOrUpdateRefreshToken(email, refreshTkn);
        return new AuthResponse(accessToken, refreshTkn);
    }
}
