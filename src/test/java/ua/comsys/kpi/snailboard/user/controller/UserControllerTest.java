package ua.comsys.kpi.snailboard.user.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ua.comsys.kpi.snailboard.user.facade.UserFacade;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
class UserControllerTest {
    private static final String URL_GET_USERS_BY_USERNAMES_WO_LIMIT = "/usersInfo/test";
    private static final String URL_GET_USERS_BY_USERNAMES_W_LIMIT = "/usersInfo/test?limit=10";
    private static final String URL_GET_CURRENT_USER = "/user";

    @Mock
    private UserFacade userFacade;

    @InjectMocks
    private final UserController testingInstance = new UserController();

    MockMvc mockMvc = MockMvcBuilders.standaloneSetup(testingInstance).build();


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @WithMockUser(username = "test", roles = {"ROLE_USER"})
    void shouldGetUsersInfoByUsernameWOLimit() throws Exception {
        mockMvc.perform(get(URL_GET_USERS_BY_USERNAMES_WO_LIMIT))
                .andExpect(status().isOk());
        verify(userFacade).getUsersByUsername("test");
    }

    @Test
    @WithMockUser(username = "test", roles = {"ROLE_USER"})
    void shouldGetUsersInfoByUsernameWLimit() throws Exception {
        mockMvc.perform(get(URL_GET_USERS_BY_USERNAMES_W_LIMIT))
                .andExpect(status().isOk());
        verify(userFacade).getUsersByUsername(eq("test"), any());
    }

    @Test
    @WithMockUser(username = "test", roles = {"ROLE_USER"})
    void shouldGetCurrentUser() throws Exception {
        mockMvc.perform(get(URL_GET_CURRENT_USER))
                .andExpect(status().isOk());
        verify(userFacade).getCurrentUserInfo();
    }
}