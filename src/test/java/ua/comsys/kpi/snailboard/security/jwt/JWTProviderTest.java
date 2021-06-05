package ua.comsys.kpi.snailboard.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;
import ua.comsys.kpi.snailboard.security.jwt.exception.TokenValidationException;
import ua.comsys.kpi.snailboard.token.access.dto.AccessTokenDTO;
import ua.comsys.kpi.snailboard.token.refresh.dto.RefreshTokenDTO;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
class JWTProviderTest {
    private static final String EMAIL = "example@example.com";
    private static final String JWT_SECRET = "jwtAccessSecret";
    private static final String SECRET_VALUE = "secret";
    private static final String REFRESH_SECRET = "jwtRefreshSecret";
    private static final String REFRESH_SECRET_VALUE = "refreshSecret";
    private static final String ACCESS_TOKEN_EXPIRATION = "jwtExpirationInMs";
    private static final Long ACCESS_TOKEN_EXPIRATION_VALUE = 60000L;
    private static final String REFRESH_TOKEN_EXPIRATION = "refreshExpirationDateInMs";
    private static final Long REFRESH_TOKEN_EXPIRATION_VALUE = 2592000000L;
    private static final Long ACCESS_TOKEN_EXPIRATION_ZERO_VALUE = 0L;
    private static final String TOKEN_EXPIRED_STRING = "Token expired";


    @InjectMocks
    private JWTProvider testingInstance = new JWTProvider();

    @BeforeEach
    public void setUp() {
        ReflectionTestUtils.setField(testingInstance, JWT_SECRET, SECRET_VALUE);
        ReflectionTestUtils.setField(testingInstance, REFRESH_SECRET, REFRESH_SECRET_VALUE);
        ReflectionTestUtils.setField(testingInstance, ACCESS_TOKEN_EXPIRATION, ACCESS_TOKEN_EXPIRATION_VALUE);
        ReflectionTestUtils.setField(testingInstance, REFRESH_TOKEN_EXPIRATION, REFRESH_TOKEN_EXPIRATION_VALUE);
    }

    @Test
    void shouldCreateAccessToken() {
        AccessTokenDTO result = testingInstance.generateAccessToken(EMAIL);

        Claims claims = Jwts.parser().setSigningKey(SECRET_VALUE).parseClaimsJws(result.getToken()).getBody();
        assertThat(claims.getSubject(), is(EMAIL));
    }

    @Test
    void shouldValidateToken() {
        AccessTokenDTO token = testingInstance.generateAccessToken(EMAIL);
        boolean result = testingInstance.validateToken(token);

        assertTrue(result);
    }

    @Test
    void shouldGetEmailFromAccessToken() {
        AccessTokenDTO token = testingInstance.generateAccessToken(EMAIL);

        String result = testingInstance.getLoginFromToken(token);

        assertThat(result, is(EMAIL));
    }

    @Test
    void shouldGetEmailFromRefreshToken() {
        RefreshTokenDTO token = testingInstance.generateRefreshToken(EMAIL);

        String result = testingInstance.getLoginFromToken(token);

        assertThat(result, is(EMAIL));
    }

    @Test
    void shouldThrowExceptionWithTokenExpiredMessageWhenExpiredToken() {
        ReflectionTestUtils.setField(testingInstance, ACCESS_TOKEN_EXPIRATION, ACCESS_TOKEN_EXPIRATION_ZERO_VALUE);

        AccessTokenDTO token = testingInstance.generateAccessToken(EMAIL);

        TokenValidationException tokenValidationException =
                assertThrows(TokenValidationException.class, () -> testingInstance.validateToken(token));
        assertEquals(TOKEN_EXPIRED_STRING, tokenValidationException.getMessage());
    }
}