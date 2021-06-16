package ua.comsys.kpi.snailboard.ticket.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ua.comsys.kpi.snailboard.column.controller.ColumnController;
import ua.comsys.kpi.snailboard.ticket.dto.CreateTicketRequest;
import ua.comsys.kpi.snailboard.ticket.service.TicketService;

import java.util.UUID;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
class TicketControllerTest {
    private static final String URL_CREATE_INITIAL_TICKET = "/ticket";

    @Mock
    private TicketService ticketService;

    @InjectMocks
    private final TicketController testingInstance = new TicketController();

    MockMvc mockMvc = MockMvcBuilders.standaloneSetup(testingInstance).build();

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void shouldCreateColumn() throws Exception {
        CreateTicketRequest request = new CreateTicketRequest();
        mockMvc.perform(post(URL_CREATE_INITIAL_TICKET)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(request)))
                .andExpect(status().isCreated());

        verify(ticketService).createInitial(request);
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}