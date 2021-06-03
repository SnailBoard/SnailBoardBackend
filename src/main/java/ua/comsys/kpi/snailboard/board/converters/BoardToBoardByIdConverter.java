package ua.comsys.kpi.snailboard.board.converters;

import org.springframework.stereotype.Component;
import ua.comsys.kpi.snailboard.board.dto.GetBoardByIdResponse;
import ua.comsys.kpi.snailboard.board.model.Board;
import ua.comsys.kpi.snailboard.column.dto.ColumnInfo;
import ua.comsys.kpi.snailboard.column.model.Columns;
import ua.comsys.kpi.snailboard.ticket.dto.TicketInfo;
import ua.comsys.kpi.snailboard.utils.Converter;

import java.util.stream.Collectors;

@Component
public class BoardToBoardByIdConverter implements Converter<Board, GetBoardByIdResponse> {
    @Override
    public GetBoardByIdResponse convert(Board source) {
        GetBoardByIdResponse target = new GetBoardByIdResponse();
        target.setName(source.getName());
        target.setDescription(source.getDescription());
        setColumns(target, source);
        return target;
    }

    private void setColumns(GetBoardByIdResponse target, Board source){
        target.setColumns(source.getColumns().stream().map(column -> {
            ColumnInfo columnInfo = new ColumnInfo();
            columnInfo.setName(column.getName());
            columnInfo.setDescription(column.getDescription());
            columnInfo.setPosition(column.getColumnPosition());
            columnInfo.setId(column.getId());
            setTickets(columnInfo, column);
            return columnInfo;
        }).collect(Collectors.toList()));
    }

    private void setTickets(ColumnInfo target, Columns source) {
        target.setTasks(source.getTickets().stream().map(ticket -> {
            TicketInfo ticketInfo = new TicketInfo();
            ticketInfo.setName(ticket.getName());
            ticketInfo.setDescription(ticket.getDescription());
            ticketInfo.setAssignee(ticket.getAssignee().getUsername());
            ticketInfo.setReporter(ticket.getReporter().getUsername());
            ticketInfo.setPosition(ticket.getColumnPosition());
            ticketInfo.setTicketNumber(ticket.getNumber());
            ticketInfo.setId(ticket.getId());
            ticketInfo.setStoryPoints(ticket.getStoryPoints());
            ticketInfo.setCreatedAt(ticket.getCreatedAt());
            ticketInfo.setUpdatedAt(ticket.getUpdatedAt());
            return ticketInfo;
        }).collect(Collectors.toList()));
    }
}
