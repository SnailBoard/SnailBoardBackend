package ua.comsys.kpi.snailboard.token.refresh.service;

public interface RefreshTokenService {
    void createOrUpdateRefreshToken(String email, String refreshToken);

    boolean validateToken(String email, String tokenToValidate);
}
