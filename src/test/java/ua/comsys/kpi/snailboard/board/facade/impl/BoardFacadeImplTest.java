package ua.comsys.kpi.snailboard.board.facade.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import ua.comsys.kpi.snailboard.board.dto.GetBoardByIdResponse;
import ua.comsys.kpi.snailboard.board.dto.GetBoardResponse;
import ua.comsys.kpi.snailboard.board.model.Board;
import ua.comsys.kpi.snailboard.board.service.BoardService;
import ua.comsys.kpi.snailboard.team.exception.InvalidTeamIdFormat;
import ua.comsys.kpi.snailboard.team.model.Team;
import ua.comsys.kpi.snailboard.team.service.TeamService;
import ua.comsys.kpi.snailboard.user.model.User;
import ua.comsys.kpi.snailboard.utils.Converter;

import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
class BoardFacadeImplTest {

    private static final String NAME_1 = "NAME_1";
    private static final String DESC_1 = "DESC_1";
    private static final String NAME_2 = "NAME_2";
    private static final String DESC_2 = "DESC_2";
    private static final UUID UUID_1 = UUID.fromString("115655a0-0bbc-44fd-92b2-df2643fa147f");
    private static final UUID UUID_2 = UUID.fromString("115655a0-0bbc-44fd-92b2-df2643fa148f");


    @Mock
    private BoardService boardService;

    @Mock
    private TeamService teamService;

    @Mock
    Converter<Board, GetBoardByIdResponse> getBoardByIdResponseConverter;

    @InjectMocks
    private final BoardFacadeImpl testingInstance = new BoardFacadeImpl();

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void shouldNotCreateIfUserNotBelongsToTeam() {
        Team team = Team.builder().id(UUID_1).build();
        when(teamService.getTeamById(UUID_1)).thenReturn(team);

        testingInstance.createInitial(NAME_1, DESC_1, UUID_1);

        verify(boardService).createInitial(NAME_1, DESC_1, team);
    }

    @Test
    void shouldConvertTeamsToDto() {
        Set<User> users = spy(new HashSet<>());
        Team team = Team.builder().id(UUID_1).users(users).build();
        List<Board> boards = new ArrayList<>();
        Board board1 = Board.builder().name(NAME_1).description(DESC_1).id(UUID_1).build();
        Board board2 = Board.builder().name(NAME_2).description(DESC_2).id(UUID_2).build();
        boards.add(board1);
        boards.add(board2);
        when(teamService.getTeamById(UUID_1)).thenReturn(team);
        when(boardService.getBoardsByTeam(team)).thenReturn(boards);
        when(users.size()).thenReturn(3);

        GetBoardResponse result = testingInstance.getBoardsByTeam(UUID_1);

        assertThat(result.getBoards().size(), is(2));
        assertThat(result.getBoards().get(0).getName(), is(NAME_1));
        assertThat(result.getBoards().get(0).getId(), is(UUID_1));
        assertThat(result.getBoards().get(1).getDescription(), is(DESC_2));
        assertThat(result.getMemberCount(), is(3));
    }

    @Test
    void shouldConvertBoardToResponse() {
        Board board = Board.builder().name(NAME_1).description(DESC_1).build();
        when(boardService.getBoardById(UUID_1)).thenReturn(board);

        testingInstance.getBoardById(UUID_1);

        verify(getBoardByIdResponseConverter).convert(board);
    }
}