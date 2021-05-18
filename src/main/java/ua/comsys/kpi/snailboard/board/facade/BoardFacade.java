package ua.comsys.kpi.snailboard.board.facade;

import ua.comsys.kpi.snailboard.board.dto.BoardInfo;
import ua.comsys.kpi.snailboard.board.dto.GetBoardResponse;

import java.util.List;

public interface BoardFacade {
    void createInitial(String name, String description, String teamId);

    GetBoardResponse getBoardsByTeam(String teamId);
}
