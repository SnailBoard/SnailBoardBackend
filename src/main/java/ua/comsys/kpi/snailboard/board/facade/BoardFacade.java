package ua.comsys.kpi.snailboard.board.facade;

import ua.comsys.kpi.snailboard.board.dto.GetBoardByIdResponse;
import ua.comsys.kpi.snailboard.board.dto.GetBoardResponse;

public interface BoardFacade {
    void createInitial(String name, String description, String teamId);

    GetBoardResponse getBoardsByTeam(String teamId);

    GetBoardByIdResponse getBoardById(String teamId);
}
