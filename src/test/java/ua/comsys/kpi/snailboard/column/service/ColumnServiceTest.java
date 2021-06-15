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
import ua.comsys.kpi.snailboard.column.dto.UpdateColumnPosition;
import ua.comsys.kpi.snailboard.column.exception.NotUniquePositionException;
import ua.comsys.kpi.snailboard.column.model.Columns;
import ua.comsys.kpi.snailboard.team.model.Team;
import ua.comsys.kpi.snailboard.team.service.TeamService;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
class ColumnServiceTest {

    public static final UUID BOARD_UUID_1 = UUID.randomUUID();
    public static final UUID COLUMN_UUID_1 = UUID.randomUUID();
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

    @Test
    public void shouldFindColumnToUpdateWhenNewPosIsBigger() {
        Set<Columns> columns = new HashSet<>();
        Columns columnToUpdate = Columns.builder().columnPosition(2).description("4").build();
        columns.add(Columns.builder().columnPosition(1).description("1").build());
        columns.add(columnToUpdate);
        columns.add(Columns.builder().columnPosition(3).description("2").build());
        columns.add(Columns.builder().columnPosition(4).description("3").build());
        columns.add(Columns.builder().columnPosition(5).description("5").build());

        Set<Columns> result = testingInstance.getColumnsEligibleForUpdate(columns, columnToUpdate, 4);

        result.forEach(column -> {
            assertThat(column.getColumnPosition(), is(Integer.parseInt(column.getDescription())));
        });
    }

    @Test
    public void shouldFindColumnToUpdateWhenNewPosIsLower() {
        Set<Columns> columns = new HashSet<>();
        Columns columnToUpdate = Columns.builder().columnPosition(4).description("2").build();
        columns.add(Columns.builder().columnPosition(1).description("1").build());
        columns.add(Columns.builder().columnPosition(2).description("3").build());
        columns.add(columnToUpdate);
        columns.add(Columns.builder().columnPosition(3).description("4").build());
        columns.add(Columns.builder().columnPosition(5).description("5").build());

        Set<Columns> result = testingInstance.getColumnsEligibleForUpdate(columns, columnToUpdate, 2);

        result.forEach(column -> {
            assertThat(column.getColumnPosition(), is(Integer.parseInt(column.getDescription())));
        });
    }

    @Test
    public void shouldUpdateColumnPosition() {
        UpdateColumnPosition updateColumnPosition = new UpdateColumnPosition(COLUMN_UUID_1, 1);
        Board board = mock(Board.class);
        Columns columnToUpdate = Columns.builder().columnPosition(3).board(board).build();

        Set<Columns> columns = new HashSet<>();
        columns.add(Columns.builder().columnPosition(1).build());
        columns.add(Columns.builder().columnPosition(2).build());
        columns.add(columnToUpdate);

        when(columnRepository.findById(COLUMN_UUID_1)).thenReturn(Optional.ofNullable(columnToUpdate));
        when(board.getColumns()).thenReturn(columns);

        testingInstance.updateColumnPositions(updateColumnPosition);

        verify(columnRepository).saveAll(any());
    }

    @Test
    public void shouldNotDoAnythingIfCurrentPosEqualsNew() {
        UpdateColumnPosition updateColumnPosition = new UpdateColumnPosition(COLUMN_UUID_1, 1);
        Board board = mock(Board.class);
        Columns columnToUpdate = Columns.builder().columnPosition(1).board(board).build();
        when(columnRepository.findById(COLUMN_UUID_1)).thenReturn(Optional.ofNullable(columnToUpdate));

        testingInstance.updateColumnPositions(updateColumnPosition);

        verify(columnRepository, never()).saveAll(any());
    }
}