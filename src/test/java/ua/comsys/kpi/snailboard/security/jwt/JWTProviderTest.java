package ua.comsys.kpi.snailboard.security.jwt;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(MockitoJUnitRunner.class)
class JWTProviderTest {
    private static final String EMAIL = "example@example.com";
    private static final String JWT_SECRET = "jwtSecret";
    private static final String SECRET_VALUE = "secret";
    private static final String REFRESH_SECRET = "jwtRefreshSecret";
    private static final String REFRESH_SECRET_VALUE = "refreshSecret";
    private static final String ACCESS_TOKEN_EXPIRATION = "jwtExpirationInMs";
    private static final Long ACCESS_TOKEN_EXPIRATION_VALUE = 60000L;
    private static final String REFRESH_TOKEN_EXPIRATION = "refreshExpirationDateInMs";
    private static final Long REFRESH_TOKEN_EXPIRATION_VALUE = 2592000000L;


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
        String result = testingInstance.generateAccessToken(EMAIL);

        Claims claims = Jwts.parser().setSigningKey(SECRET_VALUE).parseClaimsJws(result).getBody();
        assertThat(claims.getSubject(), is(EMAIL));
    }

    @Test
    void shouldValidateToken() {
        String token = testingInstance.generateAccessToken(EMAIL);
        boolean result = testingInstance.validateToken(token);

        assertTrue(result);
    }

    @Test
    void shouldGetEmailFromAccessToken() {
        String token = testingInstance.generateAccessToken(EMAIL);

        String result = testingInstance.getLoginFromToken(token);

        assertThat(result, is(EMAIL));
    }

    @Test
    void shouldGetEmailFromRefreshToken() {
        String token = testingInstance.generateRefreshToken(EMAIL);

        String result = testingInstance.getLoginFromRefreshToken(token);

        assertThat(result, is(EMAIL));
    }
}