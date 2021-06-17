package ua.comsys.kpi.snailboard.ticket.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateTicketResponse {
    UUID columnId;
    TicketInfo ticketInfo;
}
