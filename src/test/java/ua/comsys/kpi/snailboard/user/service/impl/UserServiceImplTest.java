package ua.comsys.kpi.snailboard.user.service.impl;

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

import ua.comsys.kpi.snailboard.role.dao.RoleRepository;
import ua.comsys.kpi.snailboard.role.model.Role;
import ua.comsys.kpi.snailboard.role.model.Roles;
import ua.comsys.kpi.snailboard.user.dao.UserRepository;
import ua.comsys.kpi.snailboard.user.exception.UserExistsException;
import ua.comsys.kpi.snailboard.user.model.User;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
class UserServiceImplTest {

    private static final String EMAIL = "email@email.com";
    private static final String PASSWORD = "password";


    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private final UserServiceImpl testingInstance = new UserServiceImpl();

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void shouldCreateUser() {
        User user = mock(User.class);
        Role role = mock(Role.class);
        when(roleRepository.findByCode(Roles.ROLE_USER)).thenReturn(Optional.of(role));

        testingInstance.createUser(user);

        verify(userRepository).save(user);
    }

    @Test
    void shouldNotCreateUserIfUserExists() {
        User user = mock(User.class);
        Role role = mock(Role.class);
        when(roleRepository.findByCode(Roles.ROLE_USER)).thenReturn(Optional.of(role));
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

        Assertions.assertThrows(UserExistsException.class, () -> testingInstance.createUser(user));
    }

    @Test
    void shouldFindByEmailAndPasswordIfUserExistsAndPasswordMatches() {
        User user = mock(User.class);
        when(userRepository.findByEmail(EMAIL)).thenReturn(Optional.of(user));
        when(user.getPassword()).thenReturn(PASSWORD);
        when(passwordEncoder.matches(PASSWORD, PASSWORD)).thenReturn(true);

        Optional<User> result = testingInstance.findByEmailAndPassword(EMAIL, PASSWORD);

        assertThat(result, is(Optional.of(user)));

    }

    @Test
    void shouldNotFindByEmailAndPasswordIfUserExistsAndNotPasswordMatches() {
        User user = mock(User.class);
        when(userRepository.findByEmail(EMAIL)).thenReturn(Optional.of(user));
        when(user.getPassword()).thenReturn(PASSWORD);
        when(passwordEncoder.matches(PASSWORD, PASSWORD)).thenReturn(false);

        Optional<User> result = testingInstance.findByEmailAndPassword(EMAIL, PASSWORD);

        assertThat(result, is(Optional.empty()));
    }

    @Test
    void shouldNotFindByEmailAndPasswordIfUserNotExists() {
        when(userRepository.findByEmail(EMAIL)).thenReturn(Optional.empty());

        Optional<User> result = testingInstance.findByEmailAndPassword(EMAIL, PASSWORD);

        assertThat(result, is(Optional.empty()));
    }
}