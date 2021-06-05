package ua.comsys.kpi.snailboard.board.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ua.comsys.kpi.snailboard.board.dto.CreateBoardRequest;
import ua.comsys.kpi.snailboard.board.facade.BoardFacade;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
class BoardControllerTest {
    private static final String URL_CREATE_INITIAL_BOARD = "/board";
    private static final String URL_GET_BOARD_BY_TEAM = "/board/byTeam/115655a0-0bbc-44fd-92b2-df2643fa147f";
    private static final String URL_GET_BOARD_BY_ID = "/board/115655a0-0bbc-44fd-92b2-df2643fa147f";

    private static final String NAME = "name";
    private static final String DESCRIPTION = "description";
    private static final String UUID_STRING = "115655a0-0bbc-44fd-92b2-df2643fa147f";

    @Mock
    BoardFacade boardFacade;

    @InjectMocks
    private final BoardController testingInstance = new BoardController();

    MockMvc mockMvc = MockMvcBuilders.standaloneSetup(testingInstance).build();

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void shouldCreateTeam() throws Exception {
        CreateBoardRequest request = new CreateBoardRequest(UUID_STRING, NAME, DESCRIPTION);
        mockMvc.perform(post(URL_CREATE_INITIAL_BOARD)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(request)))
                .andExpect(status().isCreated());

        verify(boardFacade).createInitial(NAME, DESCRIPTION, UUID_STRING);
    }

    @Test
    @WithMockUser(username = "test", roles = {"ROLE_USER"})
    void shouldGetBoardByTeam() throws Exception {
        mockMvc.perform(get(URL_GET_BOARD_BY_TEAM))
                .andExpect(status().isOk());
        verify(boardFacade).getBoardsByTeam(UUID_STRING);
    }

    @Test
    @WithMockUser(username = "test", roles = {"ROLE_USER"})
    void shouldGetBoardById() throws Exception {
        mockMvc.perform(get(URL_GET_BOARD_BY_ID))
                .andExpect(status().isOk());
        verify(boardFacade).getBoardById(UUID_STRING);
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}