package ua.comsys.kpi.snailboard.board.facade;

import ua.comsys.kpi.snailboard.board.dto.GetBoardByIdResponse;
import ua.comsys.kpi.snailboard.board.dto.GetBoardResponse;

import java.util.UUID;

public interface BoardFacade {
    void createInitial(String name, String description, UUID teamId);

    GetBoardResponse getBoardsByTeam(UUID teamId);

    GetBoardByIdResponse getBoardById(UUID teamId);
}
