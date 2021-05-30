package ua.comsys.kpi.snailboard.security.jwt;

import io.jsonwebtoken.*;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import ua.comsys.kpi.snailboard.security.UserDetailsImpl;
import ua.comsys.kpi.snailboard.security.jwt.exception.TokenValidationException;
import ua.comsys.kpi.snailboard.token.access.dto.AccessTokenDTO;
import ua.comsys.kpi.snailboard.token.dto.Token;
import ua.comsys.kpi.snailboard.token.refresh.dto.RefreshTokenDTO;

import java.util.Date;

@Component
@Log
public class JWTProvider {
    @Value("${jwt.refreshSecret}")
    private String jwtRefreshSecret;
    @Value("${jwt.secret}")
    private String jwtAccessSecret;
    @Value("${jwt.tokenExpiration}")
    private Long jwtExpirationInMs;
    @Value("${jwt.refreshTokenExpiration}")
    private Long refreshExpirationDateInMs;

    public AccessTokenDTO generateAccessToken(String login) {
        String token = Jwts.builder()
                .setSubject(login)
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationInMs))
                .signWith(SignatureAlgorithm.HS512, jwtAccessSecret)
                .compact();
        return new AccessTokenDTO(token, getJwtAccessSecret());
    }

    public RefreshTokenDTO generateRefreshToken(String login) {
        String token = Jwts.builder()
                .setSubject(login)
                .setExpiration(new Date(System.currentTimeMillis() + refreshExpirationDateInMs))
                .signWith(SignatureAlgorithm.HS512, jwtRefreshSecret)
                .compact();
        return new RefreshTokenDTO(token, getJwtRefreshSecret());
    }

    public boolean validateToken(Token token) {
        try {
            Jwts.parser().setSigningKey(token.getSecret()).parseClaimsJws(token.getToken());
            return true;
        } catch (Exception exception) {
            throwFormattedException(exception);
            return false;
        }
    }

    private void throwFormattedException(Exception ex) {
        if (ex instanceof ExpiredJwtException) {
            log.severe("Token expired");
            throw new TokenValidationException("Token expired");
        } else if (ex instanceof UnsupportedJwtException) {
            log.severe("Unsupported jwt");
            throw new TokenValidationException("Unsupported jwt");
        } else if (ex instanceof MalformedJwtException) {
            log.severe("Malformed jwt");
            throw new TokenValidationException("Malformed jwt");
        } else if (ex instanceof SignatureException) {
            log.severe("Invalid signature");
            throw new TokenValidationException("Invalid signature");
        }
    }


    public String getLoginFromToken(Token token) {
        Claims claims = Jwts.parser().setSigningKey(token.getSecret()).parseClaimsJws(token.getToken()).getBody();
        return claims.getSubject();
    }

    public String getCurrentUserEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        var currentUser = (UserDetailsImpl) authentication.getPrincipal();
        return currentUser.getUsername();
    }

    public String getJwtRefreshSecret() {
        return jwtRefreshSecret;
    }

    public String getJwtAccessSecret() {
        return jwtAccessSecret;
    }
}
