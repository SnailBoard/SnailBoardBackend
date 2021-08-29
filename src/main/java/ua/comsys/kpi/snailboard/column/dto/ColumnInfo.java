package ua.comsys.kpi.snailboard.column.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.comsys.kpi.snailboard.ticket.dto.TicketInfo;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ColumnInfo {
    UUID id;
    String name;
    String description;
    List<TicketInfo> tasks;
    Integer position;
}
