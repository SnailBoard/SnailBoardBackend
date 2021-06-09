package ua.comsys.kpi.snailboard.column.controller;

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
import ua.comsys.kpi.snailboard.column.dto.CreateColumnRequest;
import ua.comsys.kpi.snailboard.column.service.ColumnService;

import java.util.UUID;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
class ColumnControllerTest {

    private static final String URL_CREATE_INITIAL_COLUMN = "/column";

    private static final String NAME = "name";
    private static final String DESCRIPTION = "description";
    private static final UUID BOARD_UUID = UUID.fromString("115655a0-0bbc-44fd-92b2-df2643fa147f");
    private static final Integer BOARD_POSITION = 1;

    @Mock
    private ColumnService columnService;

    @InjectMocks
    private final ColumnController testingInstance = new ColumnController();

    MockMvc mockMvc = MockMvcBuilders.standaloneSetup(testingInstance).build();

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void shouldCreateColumn() throws Exception {
        CreateColumnRequest request = new CreateColumnRequest(NAME, DESCRIPTION, BOARD_UUID, BOARD_POSITION);
        mockMvc.perform(post(URL_CREATE_INITIAL_COLUMN)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(request)))
                .andExpect(status().isCreated());

        verify(columnService).createInitial(request);
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}