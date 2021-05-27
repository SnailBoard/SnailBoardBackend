package ua.comsys.kpi.snailboard.token.refresh.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ua.comsys.kpi.snailboard.security.jwt.exception.TokenValidationException;
import ua.comsys.kpi.snailboard.token.refresh.dao.RefreshTokenRepository;
import ua.comsys.kpi.snailboard.token.refresh.exception.TokenNotValidException;
import ua.comsys.kpi.snailboard.token.refresh.facade.impl.RefreshTokenFacadeImpl;
import ua.comsys.kpi.snailboard.token.refresh.model.RefreshToken;
import ua.comsys.kpi.snailboard.token.refresh.service.RefreshTokenService;

import java.util.Optional;

@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {
    private static final Logger LOG = LoggerFactory.getLogger(RefreshTokenFacadeImpl.class);

    @Autowired
    private RefreshTokenRepository tokenRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void createOrUpdateRefreshToken(String email, String refreshToken) {
        Optional<RefreshToken> token = tokenRepository.findByEmail(email);
        token.ifPresentOrElse(tkn -> updateToken(tkn, refreshToken), () -> createToken(email, refreshToken));
    }

    public void createToken(String email, String refreshToken) {
        RefreshToken newToken = RefreshToken.builder().email(email).refreshingToken(refreshToken).build();
        tokenRepository.save(newToken);
    }

    public void updateToken(RefreshToken token, String refreshToken) {
        token.setRefreshingToken(passwordEncoder.encode(getTokenPartToEncode(refreshToken)));
        tokenRepository.save(token);
    }

    private String getTokenPartToEncode(String token) {
        int CHARS_TO_ENCODE = 20;
        if (token.length() < CHARS_TO_ENCODE) {
            throw new TokenValidationException("Invalid token");
        }
        return token.substring(token.length() - CHARS_TO_ENCODE);
    }

    @Override
    public void validateTokenMatchWithUser(String email, String tokenToValidate) {
        Optional<RefreshToken> token = tokenRepository.findByEmail(email);
        if(!(token.isPresent() &&
                passwordEncoder.matches(getTokenPartToEncode(tokenToValidate), token.get().getRefreshingToken()))){
            LOG.warn("Invalid refresh token");
            throw new TokenNotValidException();
        }
    }
}
