package ua.comsys.kpi.snailboard.user.facade.impl;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;

import ua.comsys.kpi.snailboard.security.jwt.JWTProvider;
import ua.comsys.kpi.snailboard.token.access.dto.AccessTokenDTO;
import ua.comsys.kpi.snailboard.token.refresh.dto.RefreshTokenDTO;
import ua.comsys.kpi.snailboard.token.refresh.service.RefreshTokenService;
import ua.comsys.kpi.snailboard.user.dto.AuthRequest;
import ua.comsys.kpi.snailboard.user.dto.AuthResponse;
import ua.comsys.kpi.snailboard.user.exception.UserNotFoundException;
import ua.comsys.kpi.snailboard.user.model.User;
import ua.comsys.kpi.snailboard.user.service.UserService;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
class AuthFacadeImplTest {

    private static final String EMAIL = "email@email.com";
    private static final String PASSWORD = "password";
    private static final AccessTokenDTO ACCESS_TOKEN = new AccessTokenDTO("accessToken", "test");
    private static final RefreshTokenDTO REFRESH_TOKEN = new RefreshTokenDTO("refreshToken", "test");

    @Mock
    private UserService userService;

    @Mock
    private JWTProvider jwtProvider;

    @Mock
    private RefreshTokenService refreshTokenService;

    @Mock
    private PasswordEncoder passwordEncoder;


    @InjectMocks
    private final AuthFacadeImpl testingInstance = new AuthFacadeImpl();

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void shouldAuthUser() {
        User user = mock(User.class);
        AuthRequest request = new AuthRequest(EMAIL, PASSWORD);
        when(userService.findByEmailAndPassword(EMAIL, PASSWORD)).thenReturn(Optional.of(user));
        when(user.getEmail()).thenReturn(EMAIL);
        when(jwtProvider.generateAccessToken(EMAIL)).thenReturn(ACCESS_TOKEN);
        when(jwtProvider.generateRefreshToken(EMAIL)).thenReturn(REFRESH_TOKEN);
        when(passwordEncoder.encode(REFRESH_TOKEN.getToken())).thenReturn(REFRESH_TOKEN.getToken());

        AuthResponse result = testingInstance.authUser(request);

        verify(refreshTokenService).createOrUpdateRefreshToken(EMAIL, REFRESH_TOKEN);
        assertThat(result.getAccessToken(), is(ACCESS_TOKEN.getToken()));
        assertThat(result.getRefreshToken(), is(REFRESH_TOKEN.getToken()));
    }

    @Test
    void shouldNotAuthUserIfNotExists() {
        AuthRequest request = new AuthRequest(EMAIL, PASSWORD);
        when(userService.findByEmailAndPassword(EMAIL, PASSWORD)).thenReturn(Optional.empty());

        Assertions.assertThrows(UserNotFoundException.class, () -> testingInstance.authUser(request));

    }
}