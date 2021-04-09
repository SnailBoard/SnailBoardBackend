package ua.comsys.kpi.snailboard.token.refresh.facade.impl;

import java.util.Date;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import ua.comsys.kpi.snailboard.security.jwt.JWTProvider;
import ua.comsys.kpi.snailboard.token.refresh.service.RefreshTokenService;
import ua.comsys.kpi.snailboard.user.dto.AuthResponse;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
class RefreshTokenFacadeImplTest {
    private static final String EMAIL = "email@email.com";
    private static final String REFRESH_TOKEN = "refreshToken";
    private static final String ACCESS_TOKEN = "accessToken";
    private static final Date DATE = new Date(123L);

    @Mock
    private JWTProvider jwtProvider;

    @Mock
    private RefreshTokenService refreshTokenService;

    @InjectMocks
    private final RefreshTokenFacadeImpl testingInstance = new RefreshTokenFacadeImpl();

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void shouldRefreshToken() {
        when(jwtProvider.getLoginFromRefreshToken(REFRESH_TOKEN)).thenReturn(EMAIL);
        when(refreshTokenService.validateToken(EMAIL, REFRESH_TOKEN)).thenReturn(true);
        when(jwtProvider.generateAccessToken(EMAIL)).thenReturn(ACCESS_TOKEN);
        when(jwtProvider.getRefreshExpirationDate(REFRESH_TOKEN)).thenReturn(DATE);
        when(jwtProvider.generateRefreshToken(DATE, EMAIL)).thenReturn(REFRESH_TOKEN);

        AuthResponse result = testingInstance.refreshToken(REFRESH_TOKEN);

        verify(refreshTokenService).createOrUpdateRefreshToken(EMAIL, REFRESH_TOKEN);
        assertThat(result.getRefreshToken(), is(REFRESH_TOKEN));
        assertThat(result.getAccessToken(), is(ACCESS_TOKEN));
    }

    @Test
    void shouldNotRefreshUnValidToken() {
        when(jwtProvider.getLoginFromRefreshToken(REFRESH_TOKEN)).thenReturn(EMAIL);
        when(refreshTokenService.validateToken(EMAIL, REFRESH_TOKEN)).thenReturn(false);

        Assertions.assertThrows(RuntimeException.class, () -> testingInstance.refreshToken(REFRESH_TOKEN));
    }
}