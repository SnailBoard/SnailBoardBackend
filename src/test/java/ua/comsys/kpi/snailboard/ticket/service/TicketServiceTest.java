package ua.comsys.kpi.snailboard.ticket.service;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import ua.comsys.kpi.snailboard.board.model.Board;
import ua.comsys.kpi.snailboard.column.dto.CreateColumnRequest;
import ua.comsys.kpi.snailboard.column.exception.NotUniquePositionException;
import ua.comsys.kpi.snailboard.column.model.Columns;
import ua.comsys.kpi.snailboard.column.service.ColumnService;
import ua.comsys.kpi.snailboard.team.model.Team;
import ua.comsys.kpi.snailboard.team.service.TeamService;
import ua.comsys.kpi.snailboard.ticket.TicketRepository;
import ua.comsys.kpi.snailboard.ticket.dto.CreateTicketRequest;
import ua.comsys.kpi.snailboard.ticket.dto.TicketInfo;
import ua.comsys.kpi.snailboard.ticket.model.Ticket;
import ua.comsys.kpi.snailboard.user.service.UserService;
import ua.comsys.kpi.snailboard.utils.Converter;

import java.util.HashSet;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
class TicketServiceTest {

    public static final UUID COLUMN_UUID_1 = UUID.randomUUID();
    public static final String TICKET_NAME = "name";
    public static final String TICKET_DESCRIPTION = "description";
    private static final Columns column = new Columns();
    private static final Board board = new Board();

    @Mock
    private Converter<Ticket, TicketInfo> ticketTicketInfoConverter;

    @Mock
    private ColumnService columnService;

    @Mock
    private UserService userService;

    @Mock
    private TicketRepository ticketRepository;

    @Mock
    private TeamService teamService;

    @InjectMocks
    private final TicketService testingInstance = new TicketService();

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        HashSet<Ticket> tickets = new HashSet<>();
        column.setTickets(tickets);
        column.setBoard(board);
        column.setColumnPosition(1);
        board.setTeam(new Team());
    }

    @Test
    public void shouldCreateInitial() {
        CreateTicketRequest createTicketRequest = new CreateTicketRequest();
        createTicketRequest.setColumnPosition(2);
        createTicketRequest.setColumnId(COLUMN_UUID_1);
        createTicketRequest.setName(TICKET_NAME);
        createTicketRequest.setDescription(TICKET_DESCRIPTION);

        when(columnService.getColumnById(COLUMN_UUID_1)).thenReturn(column);

        testingInstance.createInitial(createTicketRequest);

        verify(ticketRepository).save(any(Ticket.class));
        verify(ticketTicketInfoConverter).convert(any(Ticket.class));
    }

    @Test
    public void shouldNotCreateInitialIfNotUniquePosition() {
        CreateTicketRequest createTicketRequest = new CreateTicketRequest();
        createTicketRequest.setColumnPosition(2);
        createTicketRequest.setColumnId(COLUMN_UUID_1);
        createTicketRequest.setName(TICKET_NAME);
        createTicketRequest.setDescription(TICKET_DESCRIPTION);
        column.getTickets().add(Ticket.builder().columnPosition(2).build());

        when(columnService.getColumnById(COLUMN_UUID_1)).thenReturn(column);


        Assert.assertThrows(NotUniquePositionException.class, () -> testingInstance.createInitial(createTicketRequest));

    }
}