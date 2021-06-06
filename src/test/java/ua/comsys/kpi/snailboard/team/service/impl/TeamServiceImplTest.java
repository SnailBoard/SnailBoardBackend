package ua.comsys.kpi.snailboard.team.service.impl;

import org.assertj.core.util.Sets;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import ua.comsys.kpi.snailboard.email.EmailService;
import ua.comsys.kpi.snailboard.team.dao.TeamInviteRepository;
import ua.comsys.kpi.snailboard.team.dao.TeamRepository;
import ua.comsys.kpi.snailboard.team.facade.impl.TeamFacadeImpl;
import ua.comsys.kpi.snailboard.team.model.Team;
import ua.comsys.kpi.snailboard.team.model.TeamInvite;
import ua.comsys.kpi.snailboard.team.service.TeamService;
import ua.comsys.kpi.snailboard.user.facade.UserFacade;
import ua.comsys.kpi.snailboard.user.model.User;

import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
class TeamServiceImplTest {

    private static final UUID UUID_VALUE = UUID.randomUUID();
    private static final String EMAIL = "test@email.com";
    private static final String INVITE_LINK = "http://localhost:3000/team/invite/" + UUID_VALUE;


    @Mock
    private TeamRepository teamRepository;

    @Mock
    private TeamInviteRepository teamInviteRepository;

    @Mock
    private UserFacade userFacade;

    @InjectMocks
    private final TeamServiceImpl testingInstance = new TeamServiceImpl();

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldGenerateLink(){
        Team team = Team.builder().id(UUID_VALUE).build();
        User user = User.builder().teams(Set.of(team)).build();
        TeamInvite teamInvite = TeamInvite.builder().id(UUID_VALUE).build();

        when(userFacade.getCurrentUserModel()).thenReturn(user);
        when(teamRepository.findById(UUID_VALUE)).thenReturn(Optional.of(team));
        when(teamInviteRepository.save(any(TeamInvite.class))).thenReturn(teamInvite);

        String result = testingInstance.generateLink(UUID_VALUE, EMAIL);

        assertThat(result, is(INVITE_LINK));
        verify(teamInviteRepository).save(any(TeamInvite.class));
    }
}