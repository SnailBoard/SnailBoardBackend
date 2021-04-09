package ua.comsys.kpi.snailboard.token.refresh.service.impl;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;

import ua.comsys.kpi.snailboard.token.refresh.dao.RefreshTokenRepository;
import ua.comsys.kpi.snailboard.token.refresh.model.RefreshToken;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
class RefreshTokenServiceImplTest {
    private static final String EMAIL = "email@email.com";
    private static final String REFRESH_TOKEN = "refreshToken";


    @Mock
    private RefreshTokenRepository tokenRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private final RefreshTokenServiceImpl testingInstance = new RefreshTokenServiceImpl();

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void shouldCreateRefreshTokenIfNotExists() {
        when(tokenRepository.findByEmail(EMAIL)).thenReturn(Optional.empty());

        testingInstance.createOrUpdateRefreshToken(EMAIL, REFRESH_TOKEN);

        verify(tokenRepository).save(any());
    }

    @Test
    void shouldUpdateRefreshTokenIfExists() {
        RefreshToken token = mock(RefreshToken.class);
        when(tokenRepository.findByEmail(EMAIL)).thenReturn(Optional.of(token));

        testingInstance.createOrUpdateRefreshToken(EMAIL, REFRESH_TOKEN);

        verify(tokenRepository).save(token);
    }

    @Test
    void shouldValidateValidToken() {
        RefreshToken token = mock(RefreshToken.class);
        when(tokenRepository.findByEmail(EMAIL)).thenReturn(Optional.of(token));
        when(token.getRefreshingToken()).thenReturn(REFRESH_TOKEN);
        when(passwordEncoder.matches(REFRESH_TOKEN, REFRESH_TOKEN)).thenReturn(true);

        boolean result = testingInstance.validateToken(EMAIL, REFRESH_TOKEN);

        assertTrue(result);
    }

    @Test
    void shouldValidateNotValidToken() {
        RefreshToken token = mock(RefreshToken.class);
        when(tokenRepository.findByEmail(EMAIL)).thenReturn(Optional.of(token));
        when(token.getRefreshingToken()).thenReturn(REFRESH_TOKEN);
        when(passwordEncoder.matches(REFRESH_TOKEN, REFRESH_TOKEN)).thenReturn(false);

        boolean result = testingInstance.validateToken(EMAIL, REFRESH_TOKEN);

        assertFalse(result);
    }
}