package ua.comsys.kpi.snailboard.ticket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import ua.comsys.kpi.snailboard.board.dto.GetBoardByIdResponse;
import ua.comsys.kpi.snailboard.ticket.dto.CreateTicketRequest;
import ua.comsys.kpi.snailboard.ticket.dto.TicketWithColumnResponse;
import ua.comsys.kpi.snailboard.ticket.service.TicketService;

import java.util.UUID;

@RestController
@RequestMapping("/ticket")
public class TicketController {

    @Autowired
    TicketService ticketService;

    @Secured("ROLE_USER")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public TicketWithColumnResponse initialCreate(@RequestBody CreateTicketRequest request) {
        return new TicketWithColumnResponse(request.getColumnId(), ticketService.createInitial(request));
    }

    @Secured("ROLE_USER")
    @ResponseStatus(HttpStatus.CREATED)
    @GetMapping("/{ticketId}")
    public TicketWithColumnResponse getBoardById(@PathVariable UUID ticketId) {
        return ticketService.getTicketById(ticketId);
    }
}
