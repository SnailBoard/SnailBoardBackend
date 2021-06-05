package ua.comsys.kpi.snailboard.board.service.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import ua.comsys.kpi.snailboard.board.dao.BoardRepository;
import ua.comsys.kpi.snailboard.board.model.Board;
import ua.comsys.kpi.snailboard.team.exception.UserNotBelongsToTeam;
import ua.comsys.kpi.snailboard.team.model.Team;
import ua.comsys.kpi.snailboard.user.facade.UserFacade;
import ua.comsys.kpi.snailboard.user.model.User;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
class BoardServiceImplTest {

    private static final String NAME = "NAME";
    private static final String DESC = "DESC";
    private static final UUID UUID_1 = UUID.fromString("115655a0-0bbc-44fd-92b2-df2643fa147f");
    private static final UUID UUID_2 = UUID.fromString("2d9c4f9e-307d-4e18-be56-9c4e28712910");

    @Mock
    private BoardRepository boardRepository;

    @Mock
    private UserFacade userFacade;

    @InjectMocks
    private final BoardServiceImpl testingInstance = new BoardServiceImpl();

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        Set<Team> teams = new HashSet<>();
        teams.add(Team.builder().id(UUID_1).build());
        User user = User.builder().teams(teams).build();
        when(userFacade.getCurrentUserModel()).thenReturn(user);
    }

    @Test
    void shouldNotSaveBoardIfUserNotBelongsToTeam() {
        Team testTeam = Team.builder().id(UUID_2).build();

        Assertions.assertThrows(UserNotBelongsToTeam.class, () -> testingInstance.createInitial(NAME, DESC, testTeam));
    }

    @Test
    void shouldNotCreateIfUserNotBelongsToTeam() {
        Team testTeam = Team.builder().id(UUID_1).build();

        testingInstance.createInitial(NAME, DESC, testTeam);

        verify(boardRepository).save(any(Board.class));
    }
}