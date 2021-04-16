package ua.comsys.kpi.snailboard.security.jwt;

import io.jsonwebtoken.*;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ua.comsys.kpi.snailboard.security.jwt.exception.TokenValidationException;

import java.util.Date;

@Component
@Log
public class JWTProvider {
    @Value("${jwt.refreshSecret}")
    private String jwtRefreshSecret;
    @Value("${jwt.secret}")
    private String jwtSecret;
    @Value("${jwt.tokenExpiration}")
    private Long jwtExpirationInMs;
    @Value("${jwt.refreshTokenExpiration}")
    private Long refreshExpirationDateInMs;

    public String generateAccessToken(String login) {
        return Jwts.builder()
                .setSubject(login)
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationInMs))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public String generateRefreshToken(String login) {
        return Jwts.builder()
                .setSubject(login)
                .setExpiration(new Date(System.currentTimeMillis() + refreshExpirationDateInMs))
                .signWith(SignatureAlgorithm.HS512, jwtRefreshSecret)
                .compact();
    }

    public String generateRefreshToken(Date expiration, String login) {
        return Jwts.builder()
                .setSubject(login)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS512, jwtRefreshSecret)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
        } catch (ExpiredJwtException expEx) {
            log.severe("Token expired");
            throw new TokenValidationException("Token expired");
        } catch (UnsupportedJwtException unsEx) {
            log.severe("Unsupported jwt");
            throw new TokenValidationException("Unsupported jwt");
        } catch (MalformedJwtException mjEx) {
            log.severe("Malformed jwt");
            throw new TokenValidationException("Malformed jwt");
        } catch (SignatureException sEx) {
            log.severe("Invalid signature");
            throw new TokenValidationException("Invalid signature");
        } catch (Exception e) {
            log.severe("Invalid token");
            throw new TokenValidationException("Invalid token");
        }
        return true;
    }

    public String getLoginFromToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
        return claims.getSubject();
    }

    public String getLoginFromRefreshToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(jwtRefreshSecret).parseClaimsJws(token).getBody();
        return claims.getSubject();
    }

    public Date getRefreshExpirationDate(String token) {
        Claims claims = Jwts.parser().setSigningKey(jwtRefreshSecret).parseClaimsJws(token).getBody();
        return claims.getExpiration();
    }
}
