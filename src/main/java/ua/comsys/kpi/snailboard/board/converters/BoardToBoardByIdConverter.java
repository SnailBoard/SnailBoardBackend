package ua.comsys.kpi.snailboard.board.converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.comsys.kpi.snailboard.board.dto.GetBoardByIdResponse;
import ua.comsys.kpi.snailboard.board.model.Board;
import ua.comsys.kpi.snailboard.column.dto.ColumnInfo;
import ua.comsys.kpi.snailboard.column.model.Columns;
import ua.comsys.kpi.snailboard.user.dto.UserInfoDto;
import ua.comsys.kpi.snailboard.user.model.User;
import ua.comsys.kpi.snailboard.utils.Converter;

import java.util.stream.Collectors;

@Component
public class BoardToBoardByIdConverter implements Converter<Board, GetBoardByIdResponse> {

    @Autowired
    Converter<Columns, ColumnInfo> columnsColumnInfoConverter;

    @Autowired
    Converter<User, UserInfoDto> userUserInfoDtoConverter;


    @Override
    public GetBoardByIdResponse convert(Board source) {
        GetBoardByIdResponse target = new GetBoardByIdResponse();
        target.setName(source.getName());
        target.setDescription(source.getDescription());
        target.setMembers(source.getTeam().getUsers().stream()
                .map(userUserInfoDtoConverter::convert).collect(Collectors.toList()));
        target.setColumns(source.getColumns().stream()
                .map(columnsColumnInfoConverter::convert).collect(Collectors.toList()));
        return target;
    }
}
