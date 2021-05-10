package ua.comsys.kpi.snailboard.team.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ua.comsys.kpi.snailboard.team.dto.CreateTeamsRequest;
import ua.comsys.kpi.snailboard.team.facade.TeamFacade;
import ua.comsys.kpi.snailboard.user.controller.UserController;
import ua.comsys.kpi.snailboard.user.dto.RegistrationRequest;
import ua.comsys.kpi.snailboard.user.facade.UserFacade;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
class TeamControllerTest {

    private static final String URL_CREATE_INITIAL_TEAM = "/team/createInitial";

    private static final String NAME = "name";
    private static final String DESCRIPTION = "description";


    @Mock
    private TeamFacade teamFacade;

    @InjectMocks
    private final TeamController testingInstance = new TeamController();

    MockMvc mockMvc = MockMvcBuilders.standaloneSetup(testingInstance).build();


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void shouldPerformRegistration() throws Exception {
        CreateTeamsRequest request = new CreateTeamsRequest(NAME, DESCRIPTION);
        mockMvc.perform(post(URL_CREATE_INITIAL_TEAM)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(request)))
                .andExpect(status().isCreated());

        verify(teamFacade).initialCreate(NAME, DESCRIPTION);
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}