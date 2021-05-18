package ua.comsys.kpi.snailboard.board.service;

import ua.comsys.kpi.snailboard.board.model.Board;
import ua.comsys.kpi.snailboard.team.model.Team;

import java.util.List;

public interface BoardService {
    void createInitial(String name, String description, Team team);
    List<Board> getBoardsByTeam(Team team);
}
