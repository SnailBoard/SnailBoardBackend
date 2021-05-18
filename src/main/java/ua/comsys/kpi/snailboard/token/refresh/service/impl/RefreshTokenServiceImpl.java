package ua.comsys.kpi.snailboard.token.refresh.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ua.comsys.kpi.snailboard.security.jwt.exception.TokenValidationException;
import ua.comsys.kpi.snailboard.token.refresh.dao.RefreshTokenRepository;
import ua.comsys.kpi.snailboard.token.refresh.model.RefreshToken;
import ua.comsys.kpi.snailboard.token.refresh.service.RefreshTokenService;

import java.util.Optional;

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
            if (refreshToken.length() < 20) {
                throw new TokenValidationException("Invalid token");
            }
            token.get().setRefreshingToken(passwordEncoder.encode(refreshToken.substring(refreshToken.length() - 20)));
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
        if (tokenToValidate.length() < 20) {
            throw new TokenValidationException("Invalid token");
        }
        return token.isPresent() && passwordEncoder.matches(tokenToValidate.substring(tokenToValidate.length() - 20),
                token.get().getRefreshingToken());
    }
}
