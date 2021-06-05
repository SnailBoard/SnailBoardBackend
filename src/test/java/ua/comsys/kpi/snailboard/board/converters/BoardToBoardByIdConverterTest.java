package ua.comsys.kpi.snailboard.board.converters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.util.collections.Sets;
import org.mockito.runners.MockitoJUnitRunner;
import ua.comsys.kpi.snailboard.board.dto.GetBoardByIdResponse;
import ua.comsys.kpi.snailboard.board.model.Board;
import ua.comsys.kpi.snailboard.column.dto.ColumnInfo;
import ua.comsys.kpi.snailboard.column.model.Columns;
import ua.comsys.kpi.snailboard.team.model.Team;
import ua.comsys.kpi.snailboard.user.dto.UserInfoDto;
import ua.comsys.kpi.snailboard.user.model.User;
import ua.comsys.kpi.snailboard.utils.Converter;

import java.util.Collections;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
class BoardToBoardByIdConverterTest {
    private static final String NAME = "name";
    private static final String DESCRIPTION = "description";
    private static final String FIRST_NAME = "Pavlo";

    @Mock
    Converter<Columns, ColumnInfo> columnsColumnInfoConverter;

    @Mock
    Converter<User, UserInfoDto> userUserInfoDtoConverter;

    @InjectMocks
    BoardToBoardByIdConverter testingInstance = new BoardToBoardByIdConverter();

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldConvert() {
        Columns columns = new Columns();
        ColumnInfo columnInfo = new ColumnInfo();
        columnInfo.setName(NAME);

        User user = new User();
        user.setFirstName(FIRST_NAME);

        Team team = new Team();
        team.setUsers(Collections.singletonList(user));

        UserInfoDto userInfoDto = new UserInfoDto();
        userInfoDto.setFirstName(FIRST_NAME);

        Board board = new Board();
        board.setName(NAME);
        board.setDescription(DESCRIPTION);
        board.setTeam(team);
        board.setColumns(Sets.newSet(columns));

        when(columnsColumnInfoConverter.convert(columns)).thenReturn(columnInfo);
        when(userUserInfoDtoConverter.convert(user)).thenReturn(userInfoDto);

        GetBoardByIdResponse result = testingInstance.convert(board);

        assertThat(result.getName(), is(NAME));
        assertThat(result.getColumns().size(), is(1));
        assertThat(result.getColumns().get(0).getName(), is(NAME));
        assertThat(result.getMembers().size(), is(1));
        assertThat(result.getMembers().get(0).getFirstName(), is(FIRST_NAME));
    }

}