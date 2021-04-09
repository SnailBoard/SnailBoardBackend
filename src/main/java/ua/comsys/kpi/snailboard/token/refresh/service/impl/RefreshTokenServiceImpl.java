package ua.comsys.kpi.snailboard.token.refresh.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import ua.comsys.kpi.snailboard.token.refresh.dao.RefreshTokenRepository;
import ua.comsys.kpi.snailboard.token.refresh.model.RefreshToken;
import ua.comsys.kpi.snailboard.token.refresh.service.RefreshTokenService;

@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {

    @Autowired
    private RefreshTokenRepository tokenRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void createOrUpdateRefreshToken(String email, String refreshToken) {
        Optional<RefreshToken> token = tokenRepository.findByEmail(email);
        if (token.isPresent()) {
            token.get().setRefreshingToken(refreshToken);
            tokenRepository.save(token.get());
        } else {
            RefreshToken newToken = new RefreshToken();
            newToken.setEmail(email);
            newToken.setRefreshingToken(refreshToken);
            tokenRepository.save(newToken);
        }
    }

    @Override
    public boolean validateToken(String email, String tokenToValidate) {
        Optional<RefreshToken> token = tokenRepository.findByEmail(email);
        return token.isPresent() && passwordEncoder.matches(tokenToValidate, token.get().getRefreshingToken());
    }
}
