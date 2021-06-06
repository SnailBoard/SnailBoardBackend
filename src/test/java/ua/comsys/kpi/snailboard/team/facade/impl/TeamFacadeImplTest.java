package ua.comsys.kpi.snailboard.team.facade.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import ua.comsys.kpi.snailboard.email.EmailService;
import ua.comsys.kpi.snailboard.team.dto.GetTeamResponse;
import ua.comsys.kpi.snailboard.team.model.Team;
import ua.comsys.kpi.snailboard.team.service.TeamService;
import ua.comsys.kpi.snailboard.user.facade.UserFacade;
import ua.comsys.kpi.snailboard.user.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
class TeamFacadeImplTest {


    private static final String TEST_DESC = "testDesc";
    private static final String TEST_NAME = "testName";
    private static final int USERS_COUNT = 2;
    private static final UUID UUID_VALUE = UUID.randomUUID();
    private static final String EMAIL = "pawloiwanov@gmail.com";
    private static final String INVITE_LINK = "http://localhost:3000/team/invite/" + UUID_VALUE;
    private static final String TEMPLATE_NAME = "invitation.html";
    private static final String INVITATION_SUBJECT = "Snailboard team invitation";

    @Mock
    private UserFacade userService;

    @Mock
    private TeamService teamService;

    @Mock
    private EmailService emailService;

    @InjectMocks
    private final TeamFacadeImpl testingInstance = new TeamFacadeImpl();

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void shouldCreateTeam() {
        User user = new User();
        when(userService.getCurrentUserModel()).thenReturn(user);

        testingInstance.initialCreate(TEST_NAME, TEST_DESC);

        verify(teamService).create(any());
    }

    @Test
    void shouldMapTeamToResponse() {
        User user = new User();
        List<Team> teams = new ArrayList<>();
        List<User> users = List.of(user, user);
        teams.add(Team.builder().name(TEST_NAME).description(TEST_DESC).users(users).build());
        when(userService.getCurrentUserModel()).thenReturn(user);
        when(teamService.getTeamsByUser(user)).thenReturn(teams);

        List<GetTeamResponse> result = testingInstance.getTeamsForCurrentUser();

        verify(teamService).getTeamsByUser(user);
        assertThat(result.get(0).getMemberCount(), is(USERS_COUNT));
        assertThat(result.get(0).getName(), is(TEST_NAME));
    }

    @Test
    void shouldGenerateAndSendLink() {
        testingInstance.generateAndSendLink(UUID_VALUE, EMAIL);

        verify(emailService).sendEmail(eq(EMAIL), any(HashMap.class), eq(TEMPLATE_NAME), eq(INVITATION_SUBJECT));
    }
}