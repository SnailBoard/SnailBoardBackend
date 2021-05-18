package ua.comsys.kpi.snailboard.board.facade.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import ua.comsys.kpi.snailboard.board.service.BoardService;
import ua.comsys.kpi.snailboard.team.exception.InvalidTeamIdFormat;
import ua.comsys.kpi.snailboard.team.model.Team;
import ua.comsys.kpi.snailboard.team.service.TeamService;

import java.util.UUID;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
class BoardFacadeImplTest {

    private static final String NAME = "NAME";
    private static final String DESC = "DESC";
    private static final UUID UUID_1 = UUID.fromString("115655a0-0bbc-44fd-92b2-df2643fa147f");
    private static final String UUID_1_STRING = "115655a0-0bbc-44fd-92b2-df2643fa147f";
    private static final String INVALID_UUID_STRING = "invalid";

    @Mock
    private BoardService boardService;

    @Mock
    private TeamService teamService;

    @InjectMocks
    private final BoardFacadeImpl testingInstance = new BoardFacadeImpl();

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void shouldSaveBoardIfUserBelongsToTeam() {
        Assertions.assertThrows(InvalidTeamIdFormat.class, () -> testingInstance.createInitial(NAME, DESC, INVALID_UUID_STRING));
    }

    @Test
    void shouldNotCreateIfUserNotBelongsToTeam() {
        Team team = Team.builder().id(UUID_1).build();
        when(teamService.getTeamById(UUID_1)).thenReturn(team);

        testingInstance.createInitial(NAME, DESC, UUID_1_STRING);

        verify(boardService).createInitial(NAME, DESC, team);
    }
}