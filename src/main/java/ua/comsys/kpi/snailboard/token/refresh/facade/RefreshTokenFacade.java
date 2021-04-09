package ua.comsys.kpi.snailboard.token.refresh.facade;

import ua.comsys.kpi.snailboard.user.dto.AuthResponse;

public interface RefreshTokenFacade {
    AuthResponse refreshToken(String refreshToken);
}
