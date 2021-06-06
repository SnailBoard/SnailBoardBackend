package ua.comsys.kpi.snailboard.column.convertors;

import org.assertj.core.util.Sets;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import ua.comsys.kpi.snailboard.column.dto.ColumnInfo;
import ua.comsys.kpi.snailboard.column.model.Columns;
import ua.comsys.kpi.snailboard.ticket.converters.TicketToTicketInfoConverter;
import ua.comsys.kpi.snailboard.ticket.dto.TicketInfo;
import ua.comsys.kpi.snailboard.ticket.model.Ticket;
import ua.comsys.kpi.snailboard.user.dto.UserInfoDto;
import ua.comsys.kpi.snailboard.user.model.User;
import ua.comsys.kpi.snailboard.utils.Converter;

import java.util.Arrays;
import java.util.Collections;
import java.util.Set;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
class ColumnToColumnInfoConverterTest {
    private static final String NAME = "name";
    private static final String DESCRIPTION = "description";

    @Mock
    Converter<Ticket, TicketInfo> ticketTicketInfoConverter;

    @InjectMocks
    ColumnToColumnInfoConverter testingInstance = new ColumnToColumnInfoConverter();

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldConvert() {
        Ticket ticket = new Ticket();
        ticket.setName(NAME);

        TicketInfo ticketInfo = new TicketInfo();
        ticketInfo.setName(NAME);

        Columns column = new Columns();
        column.setName(NAME);
        column.setDescription(DESCRIPTION);
        column.setTickets(Set.of(ticket));

        when(ticketTicketInfoConverter.convert(ticket)).thenReturn(ticketInfo);

        ColumnInfo result = testingInstance.convert(column);

        assertThat(result.getName(), is(NAME));
        assertThat(result.getTasks().size(), is(1));
        assertThat(result.getTasks().get(0).getName(), is(NAME));
    }

}