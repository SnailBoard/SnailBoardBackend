package ua.comsys.kpi.snailboard.board.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.comsys.kpi.snailboard.column.dto.ColumnInfo;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetBoardByIdResponse {
    String name;
    String description;
    List<ColumnInfo> columns;
}
