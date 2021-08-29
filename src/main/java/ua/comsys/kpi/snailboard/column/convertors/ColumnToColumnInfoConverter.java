package ua.comsys.kpi.snailboard.column.convertors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.comsys.kpi.snailboard.column.dto.ColumnInfo;
import ua.comsys.kpi.snailboard.column.model.Columns;
import ua.comsys.kpi.snailboard.ticket.dto.TicketInfo;
import ua.comsys.kpi.snailboard.ticket.model.Ticket;
import ua.comsys.kpi.snailboard.utils.Converter;

import java.util.stream.Collectors;

@Component
public class ColumnToColumnInfoConverter implements Converter<Columns, ColumnInfo> {

    @Autowired
    Converter<Ticket, TicketInfo> ticketTicketInfoConverter;

    @Override
    public ColumnInfo convert(Columns source) {
        ColumnInfo columnInfo = new ColumnInfo();
        columnInfo.setName(source.getName());
        columnInfo.setDescription(source.getDescription());
        columnInfo.setPosition(source.getColumnPosition());
        columnInfo.setId(source.getId());
        columnInfo.setTasks(source.getTickets().stream()
                .map(ticketTicketInfoConverter::convert).collect(Collectors.toList()));
        return columnInfo;
    }
}
