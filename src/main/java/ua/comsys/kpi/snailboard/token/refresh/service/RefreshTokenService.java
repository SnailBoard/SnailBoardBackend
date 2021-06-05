package ua.comsys.kpi.snailboard.token.refresh.service;

import ua.comsys.kpi.snailboard.token.refresh.dto.RefreshTokenDTO;

public interface RefreshTokenService {
    void createOrUpdateRefreshToken(String email, RefreshTokenDTO refreshToken);

    void validateTokenMatchWithUser(String email, String tokenToValidate);
}
