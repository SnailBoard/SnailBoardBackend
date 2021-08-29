package ua.comsys.kpi.snailboard.ticket.converters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import ua.comsys.kpi.snailboard.ticket.dto.TicketInfo;
import ua.comsys.kpi.snailboard.ticket.model.Ticket;
import ua.comsys.kpi.snailboard.user.dto.UserInfoDto;
import ua.comsys.kpi.snailboard.user.model.User;
import ua.comsys.kpi.snailboard.utils.Converter;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
class TicketToTicketInfoConverterTest {
    private static final String NAME = "name";
    private static final String DESCRIPTION = "description";
    private static final String REPORTER_NAME = "Pavlo";
    private static final String ASSIGNEE_NAME = "Rost";

    @Mock
    Converter<User, UserInfoDto> userUserInfoDtoConverter;

    @InjectMocks
    TicketToTicketInfoConverter testingInstance = new TicketToTicketInfoConverter();

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldConvert() {
        User user1 = new User();
        user1.setFirstName(REPORTER_NAME);

        User user2 = new User();
        user2.setFirstName(REPORTER_NAME);

        UserInfoDto userInfoDto1 = new UserInfoDto();
        userInfoDto1.setFirstName(REPORTER_NAME);

        UserInfoDto userInfoDto2 = new UserInfoDto();
        userInfoDto2.setFirstName(ASSIGNEE_NAME);

        Ticket ticket = new Ticket();
        ticket.setName(NAME);
        ticket.setDescription(DESCRIPTION);
        ticket.setReporter(user1);
        ticket.setAssignee(user2);

        when(userUserInfoDtoConverter.convert(user1)).thenReturn(userInfoDto1);
        when(userUserInfoDtoConverter.convert(user2)).thenReturn(userInfoDto2);

        TicketInfo result = testingInstance.convert(ticket);

        assertThat(result.getName(), is(NAME));
        assertThat(result.getReporter().getFirstName(), is(REPORTER_NAME));
        assertThat(result.getAssignee().getFirstName(), is(ASSIGNEE_NAME));
    }

}