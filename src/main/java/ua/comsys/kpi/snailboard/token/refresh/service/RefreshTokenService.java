package ua.comsys.kpi.snailboard.token.refresh.service;

public interface RefreshTokenService {
    void createOrUpdateRefreshToken(String email, String refreshToken);
}
