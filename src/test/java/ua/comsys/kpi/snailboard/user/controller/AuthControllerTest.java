package ua.comsys.kpi.snailboard.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ua.comsys.kpi.snailboard.token.refresh.facade.RefreshTokenFacade;
import ua.comsys.kpi.snailboard.user.dto.AuthRequest;
import ua.comsys.kpi.snailboard.user.dto.RefreshTokenRequest;
import ua.comsys.kpi.snailboard.user.dto.RegistrationRequest;
import ua.comsys.kpi.snailboard.user.exception.UserExistsException;
import ua.comsys.kpi.snailboard.user.facade.AuthFacade;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
class AuthControllerTest {
    private static final String EMAIL = "example@gmai.com";
    private static final String PASSWORD = "test";
    private static final String USERNAME = "Test";
    private static final String FIRST_NAME = "Test";
    private static final String REFRESH_TOKEN = "test";

    private static final String URL_REGISTER = "/register";
    private static final String URL_AUTH = "/auth";
    private static final String URL_REFRESH = "/refresh";

    @Mock
    private AuthFacade authFacade;

    @Mock
    private RefreshTokenFacade refreshTokenFacade;

    @InjectMocks
    private final AuthController testingInstance = new AuthController();

    MockMvc mockMvc = MockMvcBuilders.standaloneSetup(testingInstance).build();


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void shouldPerformRegistration() throws Exception {
        RegistrationRequest request = new RegistrationRequest(EMAIL, PASSWORD, USERNAME, FIRST_NAME);
        mockMvc.perform(post(URL_REGISTER)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(request)))
                .andExpect(status().isCreated());

        verify(authFacade).createUser(any());
    }

    @Test
    void shouldReturnErrorResponseOnSavedEmailUsage() throws Exception {
        RegistrationRequest request = new RegistrationRequest(EMAIL, PASSWORD, USERNAME, FIRST_NAME);

        doThrow(UserExistsException.class).when(authFacade).createUser(request);

        mockMvc.perform(post(URL_REGISTER)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(request)))
                .andExpect(status().isForbidden())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof UserExistsException));
    }

    @Test
    void shouldPerformAuth() throws Exception {
        AuthRequest request = new AuthRequest(EMAIL, PASSWORD);
        mockMvc.perform(post(URL_AUTH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(request)))
                .andExpect(status().isOk());

        verify(authFacade).authUser(any());
    }

    @Test
    void shouldPerformRefresh() throws Exception {
        RefreshTokenRequest request = new RefreshTokenRequest(REFRESH_TOKEN);
        mockMvc.perform(post(URL_REFRESH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(request)))
                .andExpect(status().isOk());

        verify(refreshTokenFacade).refreshToken(REFRESH_TOKEN);
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}