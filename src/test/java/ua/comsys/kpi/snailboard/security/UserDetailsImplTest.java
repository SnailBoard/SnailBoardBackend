package ua.comsys.kpi.snailboard.security;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.core.userdetails.UserDetails;

import ua.comsys.kpi.snailboard.role.model.Role;
import ua.comsys.kpi.snailboard.user.model.User;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
class UserDetailsImplTest {
    private static final String EMAIL = "email@email.com";
    private static final String PASSWORD = "password";

    @Test
    void shouldConvertUser() {
        User user = mock(User.class);
        List<Role> roles = new ArrayList<>();
        when(user.getEmail()).thenReturn(EMAIL);
        when(user.getPassword()).thenReturn(PASSWORD);
        when(user.getRoles()).thenReturn(roles);

        UserDetails result = UserDetailsImpl.fromUserEntityToUserDetails(user);

        assertThat(result.getUsername(), is(EMAIL));
        assertThat(result.getPassword(), is(PASSWORD));
    }
}