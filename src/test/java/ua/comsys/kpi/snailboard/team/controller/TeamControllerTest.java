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

import java.util.UUID;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
class TeamControllerTest {

    private static final String URL_CREATE_INITIAL_TEAM = "/team";
    private static final String URL_GET_TEAMS = "/team";
    private static final String URL_GENERATE_INVITE = "/team/generateLink";
    private static final String URL_ACCEPT_INVITE = "/team/acceptInvitation/";

    private static final String NAME = "name";
    private static final UUID UUID_VALUE = UUID.randomUUID();
    private static final String TEAM_ID = "?teamId=" + UUID_VALUE;
    private static final String EMAIL_VALUE = "pawloiwanov@gmail.com";
    private static final String EMAIL = "&userEmail=" + EMAIL_VALUE;
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
    void shouldCreateTeam() throws Exception {
        CreateTeamsRequest request = new CreateTeamsRequest(NAME, DESCRIPTION, "dsdas");
        mockMvc.perform(post(URL_CREATE_INITIAL_TEAM)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(request)))
                .andExpect(status().isCreated());

        verify(teamFacade).initialCreate(NAME, DESCRIPTION, "dsasdas");
    }

    @Test
    @WithMockUser(username = "test", roles = {"ROLE_USER"})
    void shouldGetTeamForUser() throws Exception {
        mockMvc.perform(get(URL_GET_TEAMS))
                .andExpect(status().isOk());
        verify(teamFacade).getTeamsForCurrentUser();
    }

    @Test
    void shouldGenerateAndSendInviteLinkForTeamToUser() throws Exception {
        mockMvc.perform(post(URL_GENERATE_INVITE + TEAM_ID + EMAIL).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(teamFacade).generateAndSendLink(UUID_VALUE, EMAIL_VALUE);
    }

    @Test
    void shouldAcceptInvitation() throws Exception {
        mockMvc.perform(put(URL_ACCEPT_INVITE + UUID_VALUE).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(teamFacade).acceptTeamInvitation(UUID_VALUE);
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}