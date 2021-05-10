package ua.comsys.kpi.snailboard.user.facade.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import ua.comsys.kpi.snailboard.security.jwt.JWTProvider;
import ua.comsys.kpi.snailboard.user.dto.UserInfoDto;
import ua.comsys.kpi.snailboard.user.model.User;
import ua.comsys.kpi.snailboard.user.service.UserService;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
class UserFacadeImplTest {

    private static final String USERNAME = "test";
    private static final String USERNAME_TEST1 = "test1";
    private static final String USERNAME_TEST2 = "test2";
    private static final int LIST_SIZE_WO_LIMIT = 2;

    @Mock
    private UserService userService;

    @Mock
    private JWTProvider jwtProvider;

    @InjectMocks
    private final UserFacadeImpl testingInstance = new UserFacadeImpl();

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void shouldMapUserToUserInfo() {
        List<User> users = new ArrayList<>();
        users.add(User.builder().username(USERNAME_TEST1).build());
        users.add(User.builder().username(USERNAME_TEST2).build());
        when(userService.getUsersByUsername(USERNAME)).thenReturn(users);


        List<UserInfoDto> result = testingInstance.getUsersByUsername(USERNAME);

        assertThat(result.size(), is(LIST_SIZE_WO_LIMIT));
        assertThat(result.get(0).getUsername(), is(USERNAME_TEST1));
        assertThat(result.get(1).getUsername(), is(USERNAME_TEST2));
    }
}