package ua.comsys.kpi.snailboard.ticket.converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.comsys.kpi.snailboard.ticket.dto.TicketInfo;
import ua.comsys.kpi.snailboard.ticket.model.Ticket;
import ua.comsys.kpi.snailboard.user.dto.UserInfoDto;
import ua.comsys.kpi.snailboard.user.model.User;
import ua.comsys.kpi.snailboard.utils.Converter;

@Component
public class TicketToTicketInfoConverter implements Converter<Ticket, TicketInfo> {

    @Autowired
    Converter<User, UserInfoDto> userUserInfoDtoConverter;

    @Override
    public TicketInfo convert(Ticket source) {
        TicketInfo ticketInfo = new TicketInfo();
        ticketInfo.setName(source.getName());
        ticketInfo.setDescription(source.getDescription());
        ticketInfo.setAssignee(userUserInfoDtoConverter.convert(source.getAssignee()));
        ticketInfo.setReporter(userUserInfoDtoConverter.convert(source.getReporter()));
        ticketInfo.setPosition(source.getColumnPosition());
        ticketInfo.setTicketNumber(source.getNumber());
        ticketInfo.setId(source.getId());
        ticketInfo.setStoryPoints(source.getStoryPoints());
        ticketInfo.setCreatedAt(source.getCreatedAt());
        ticketInfo.setUpdatedAt(source.getUpdatedAt());
        return ticketInfo;
    }
}
