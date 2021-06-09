package ua.comsys.kpi.snailboard.column.service;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import ua.comsys.kpi.snailboard.board.model.Board;
import ua.comsys.kpi.snailboard.board.service.BoardService;
import ua.comsys.kpi.snailboard.column.dao.ColumnRepository;
import ua.comsys.kpi.snailboard.column.dto.CreateColumnRequest;
import ua.comsys.kpi.snailboard.column.exception.NotUniquePositionException;
import ua.comsys.kpi.snailboard.column.model.Columns;
import ua.comsys.kpi.snailboard.team.model.Team;
import ua.comsys.kpi.snailboard.team.service.TeamService;

import java.util.HashSet;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
class ColumnServiceTest {

    public static final UUID BOARD_UUID_1 = UUID.randomUUID();
    public static final String BOARD_NAME = "name";
    public static final String BOARD_DESCRIPTION = "description";
    private static final Columns column = new Columns();
    private static final Board board = new Board();
    @Mock
    private BoardService boardService;

    @Mock
    private TeamService teamService;

    @Mock
    private ColumnRepository columnRepository;


    @InjectMocks
    private final ColumnService testingInstance = new ColumnService();

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        HashSet<Columns> columns = new HashSet<>();
        columns.add(column);
        column.setColumnPosition(1);
        board.setColumns(columns);
        board.setTeam(new Team());
    }

    @Test
    public void shouldCreateInitial() {
        CreateColumnRequest createColumnRequest = new CreateColumnRequest();
        createColumnRequest.setColumnPosition(2);
        createColumnRequest.setBoardId(BOARD_UUID_1);
        createColumnRequest.setName(BOARD_NAME);
        createColumnRequest.setDescription(BOARD_DESCRIPTION);

        when(boardService.getBoardById(BOARD_UUID_1)).thenReturn(board);

        testingInstance.createInitial(createColumnRequest);

        verify(columnRepository).save(any(Columns.class));
    }

    @Test
    public void shouldNotCreateInitialIfNotUniquePosition() {
        CreateColumnRequest createColumnRequest = new CreateColumnRequest();
        createColumnRequest.setColumnPosition(2);
        createColumnRequest.setBoardId(BOARD_UUID_1);
        createColumnRequest.setName(BOARD_NAME);
        createColumnRequest.setDescription(BOARD_DESCRIPTION);
        board.getColumns().add(Columns.builder().columnPosition(2).build());

        when(boardService.getBoardById(BOARD_UUID_1)).thenReturn(board);

        Assert.assertThrows(NotUniquePositionException.class, () -> testingInstance.createInitial(createColumnRequest));

    }
}