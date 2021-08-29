package ua.comsys.kpi.snailboard.ticket.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.comsys.kpi.snailboard.column.exception.NotUniquePositionException;
import ua.comsys.kpi.snailboard.column.model.Columns;
import ua.comsys.kpi.snailboard.column.service.ColumnService;
import ua.comsys.kpi.snailboard.team.service.TeamService;
import ua.comsys.kpi.snailboard.ticket.dao.TicketRepository;
import ua.comsys.kpi.snailboard.ticket.dto.CreateTicketRequest;
import ua.comsys.kpi.snailboard.ticket.dto.TicketInfo;
import ua.comsys.kpi.snailboard.ticket.dto.TicketWithColumnResponse;
import ua.comsys.kpi.snailboard.ticket.model.Ticket;
import ua.comsys.kpi.snailboard.ticket.model.TicketNumber;
import ua.comsys.kpi.snailboard.user.model.User;
import ua.comsys.kpi.snailboard.user.service.UserService;
import ua.comsys.kpi.snailboard.utils.Converter;

import java.util.UUID;

@Service
public class TicketService {

    @Autowired
    private Converter<Ticket, TicketInfo> ticketTicketInfoConverter;

    @Autowired
    private ColumnService columnService;

    @Autowired
    private UserService userService;

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private TeamService teamService;

    @Autowired
    private TicketNumberService ticketNumberService;

    @Transactional
    public TicketInfo createInitial(CreateTicketRequest request) {
        Columns column = columnService.getColumnById(request.getColumnId());
        teamService.validateUserBelongsToTeam(column.getBoard().getTeam());
        validatePositionIsUniqueForColumn(column, request.getColumnPosition());
        User assignee = userService.getUserById(request.getAssigneeId());
        User reporter = userService.getUserById(request.getReporterId());
        ticketNumberService.incrementForBoard(column.getBoard().getId());
        TicketNumber ticketNumber = ticketNumberService.getByBoardId(column.getBoard().getId());

        Ticket ticket = Ticket.builder()
                .name(request.getName())
                .description(request.getDescription())
                .columnPosition(request.getColumnPosition())
                .assignee(assignee)
                .reporter(reporter)
                .column(column)
                .number(ticketNumber.getNumber() + 1)
                .storyPoints(request.getStoryPoints())
                .build();

        ticketRepository.save(ticket);

        return ticketTicketInfoConverter.convert(ticket);
    }

    private void validatePositionIsUniqueForColumn(Columns column, int position){
        if (column.getTickets().stream().anyMatch(ticket -> ticket.getColumnPosition() == position)){
            throw new NotUniquePositionException();
        }
    }

    public TicketWithColumnResponse getTicketById(UUID ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId).orElseThrow(RuntimeException::new);
        teamService.validateUserBelongsToTeam(ticket.getColumn().getBoard().getTeam());
        return new TicketWithColumnResponse(ticket.getColumn().getId(), ticketTicketInfoConverter.convert(ticket));
    }
}
