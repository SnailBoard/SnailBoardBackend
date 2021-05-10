package ua.comsys.kpi.snailboard.team.facade.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import ua.comsys.kpi.snailboard.team.service.TeamService;
import ua.comsys.kpi.snailboard.user.facade.UserFacade;
import ua.comsys.kpi.snailboard.user.model.User;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
class TeamFacadeImplTest {


    private static final String TEST_DESC = "testDesc";
    private static final String TEST_NAME = "testName";

    @Mock
    private UserFacade userService;

    @Mock
    private TeamService teamService;

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

}