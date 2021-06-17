package ua.comsys.kpi.snailboard.ticket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import ua.comsys.kpi.snailboard.ticket.dto.CreateTicketRequest;
import ua.comsys.kpi.snailboard.ticket.dto.CreateTicketResponse;
import ua.comsys.kpi.snailboard.ticket.dto.TicketInfo;
import ua.comsys.kpi.snailboard.ticket.service.TicketService;

@RestController
@RequestMapping("/ticket")
public class TicketController {

    @Autowired
    TicketService ticketService;

    @Secured("ROLE_USER")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public CreateTicketResponse initialCreate(@RequestBody CreateTicketRequest request) {
        return new CreateTicketResponse(request.getColumnId(), ticketService.createInitial(request));
    }
}
