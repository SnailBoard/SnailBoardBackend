package ua.comsys.kpi.snailboard.security;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import ua.comsys.kpi.snailboard.role.model.Role;
import ua.comsys.kpi.snailboard.user.model.User;
import ua.comsys.kpi.snailboard.user.service.UserService;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
class UserDetailsServiceImplTest {
    private static final String EMAIL = "email@email.com";
    private static final String PASSWORD = "password";

    @Mock
    private UserService userService;

    @InjectMocks
    private final UserDetailsServiceImpl testingInstance = new UserDetailsServiceImpl();

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void shouldLoadUserByUsername() {
        User user = mock(User.class);
        List<Role> roles = new ArrayList<>();
        when(userService.findByEmail(EMAIL)).thenReturn(Optional.of(user));
        when(user.getEmail()).thenReturn(EMAIL);
        when(user.getPassword()).thenReturn(PASSWORD);
        when(user.getRoles()).thenReturn(roles);

        UserDetails result = testingInstance.loadUserByUsername(EMAIL);

        assertThat(result.getUsername(), is(EMAIL));
        assertThat(result.getPassword(), is(PASSWORD));
    }

    @Test
    void shouldNotLoadUserByUsernameIfNotExists() {
        when(userService.findByEmail(EMAIL)).thenReturn(Optional.empty());

        Assertions.assertThrows(UsernameNotFoundException.class, () -> testingInstance.loadUserByUsername(EMAIL));
    }
}