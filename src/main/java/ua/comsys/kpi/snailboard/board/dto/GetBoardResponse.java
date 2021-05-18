package ua.comsys.kpi.snailboard.board.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetBoardResponse {
    List<BoardInfo> boards;
    Integer memberCount;
}
