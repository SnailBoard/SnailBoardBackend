package ua.comsys.kpi.snailboard.board.facade.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.comsys.kpi.snailboard.board.facade.BoardFacade;
import ua.comsys.kpi.snailboard.board.service.BoardService;
import ua.comsys.kpi.snailboard.team.exception.InvalidTeamIdFormat;
import ua.comsys.kpi.snailboard.team.model.Team;
import ua.comsys.kpi.snailboard.team.service.TeamService;

import java.util.UUID;

@Component
public class BoardFacadeImpl implements BoardFacade {

    @Autowired
    TeamService teamService;

    @Autowired
    BoardService boardService;

    @Override
    public void createInitial(String name, String description, String teamId) {
        validateIdType(teamId);
        Team team = teamService.getTeamById(UUID.fromString(teamId));
        boardService.createInitial(name, description, team);
    }

    private void validateIdType(String teamId) {
        try {
            UUID.fromString(teamId);
        } catch (Exception ex) {
            throw new InvalidTeamIdFormat();
        }
    }
}
