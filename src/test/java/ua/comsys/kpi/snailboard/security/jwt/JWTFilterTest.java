package ua.comsys.kpi.snailboard.security.jwt;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import ua.comsys.kpi.snailboard.role.model.Role;
import ua.comsys.kpi.snailboard.role.model.Roles;
import ua.comsys.kpi.snailboard.security.UserDetailsServiceImpl;
import ua.comsys.kpi.snailboard.user.exception.UserNotFoundException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
class JWTFilterTest {
    public static final String AUTHORIZATION = "Authorization";
    public static final String TOKEN = "Bearer test";
    public static final String PARSE_TOKEN = "test";
    public static final String EMAIL = "example@example.com";

    @Mock
    private JWTProvider jwtProvider;

    @Mock
    private UserDetailsServiceImpl userDetailsService;

    @InjectMocks
    private final JWTFilter testingInstance = new JWTFilter();

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void shouldFilterValidToken() throws IOException, ServletException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        ServletResponse response = mock(ServletResponse.class);
        FilterChain filterChain = mock(FilterChain.class);
        UserDetails userDetails = mock(UserDetails.class);
        Role role = mock(Role.class);
        when(request.getHeader(AUTHORIZATION)).thenReturn(TOKEN);
        when(jwtProvider.validateToken(PARSE_TOKEN)).thenReturn(true);
        when(jwtProvider.getLoginFromToken(PARSE_TOKEN)).thenReturn(EMAIL);
        when(userDetailsService.loadUserByUsername(EMAIL)).thenReturn(userDetails);
        testingInstance.doFilter(request, response, filterChain);

        verify(filterChain).doFilter(request, response);
    }
}