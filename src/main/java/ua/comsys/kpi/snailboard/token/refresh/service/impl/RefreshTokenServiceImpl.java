package ua.comsys.kpi.snailboard.token.refresh.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.comsys.kpi.snailboard.token.refresh.dao.RefreshTokenRepository;
import ua.comsys.kpi.snailboard.token.refresh.model.RefreshToken;
import ua.comsys.kpi.snailboard.token.refresh.service.RefreshTokenService;

import java.util.Optional;

@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final RefreshTokenRepository tokenRepository;

    @Autowired
    public RefreshTokenServiceImpl(RefreshTokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    @Override
    public void createOrUpdateRefreshToken(String email, String refreshToken) {
        Optional<RefreshToken> token = tokenRepository.findByEmail(email);
        if (token.isPresent()) {
            token.get().setRefreshingToken(refreshToken);
            tokenRepository.save(token.get());
        } else  {
            RefreshToken newToken = new RefreshToken();
            newToken.setEmail(email);
            newToken.setRefreshingToken(refreshToken);
            tokenRepository.save(newToken);
        }
    }
}
