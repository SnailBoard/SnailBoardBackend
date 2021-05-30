package ua.comsys.kpi.snailboard.board.facade.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.comsys.kpi.snailboard.board.dto.BoardInfo;
import ua.comsys.kpi.snailboard.board.dto.GetBoardResponse;
import ua.comsys.kpi.snailboard.board.facade.BoardFacade;
import ua.comsys.kpi.snailboard.board.model.Board;
import ua.comsys.kpi.snailboard.board.service.BoardService;
import ua.comsys.kpi.snailboard.team.exception.InvalidTeamIdFormat;
import ua.comsys.kpi.snailboard.team.model.Team;
import ua.comsys.kpi.snailboard.team.service.TeamService;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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

    public GetBoardResponse getBoardsByTeam(String teamId) {
        validateIdType(teamId);
        Team team = teamService.getTeamById(UUID.fromString(teamId));
        List<Board> boards = boardService.getBoardsByTeam(team);
        List<BoardInfo> boardsInfo = boards.stream()
                .map(board -> new BoardInfo(board.getName(), board.getDescription()))
                .collect(Collectors.toList());
        return new GetBoardResponse(boardsInfo, team.getUsers().size());
    }

    private void validateIdType(String teamId) {
        try {
            UUID.fromString(teamId);
        } catch (Exception ex) {
            throw new InvalidTeamIdFormat();
        }
    }
}
