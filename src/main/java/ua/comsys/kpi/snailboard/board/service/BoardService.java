package ua.comsys.kpi.snailboard.board.service;

import ua.comsys.kpi.snailboard.board.model.Board;
import ua.comsys.kpi.snailboard.team.model.Team;

import java.util.List;
import java.util.UUID;

public interface BoardService {
    void createInitial(String name, String description, Team team);

    List<Board> getBoardsByTeam(Team team);

    Board getBoardById(UUID id);
}
