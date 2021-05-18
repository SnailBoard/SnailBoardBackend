package ua.comsys.kpi.snailboard.board.service;

import ua.comsys.kpi.snailboard.team.model.Team;

public interface BoardService {
    void createInitial(String name, String description, Team team);
}
