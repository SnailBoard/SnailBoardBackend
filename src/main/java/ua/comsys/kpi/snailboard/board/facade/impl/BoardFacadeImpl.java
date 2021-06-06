package ua.comsys.kpi.snailboard.board.facade.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.comsys.kpi.snailboard.board.dto.BoardPreviewInfo;
import ua.comsys.kpi.snailboard.board.dto.GetBoardByIdResponse;
import ua.comsys.kpi.snailboard.board.dto.GetBoardResponse;
import ua.comsys.kpi.snailboard.board.facade.BoardFacade;
import ua.comsys.kpi.snailboard.board.model.Board;
import ua.comsys.kpi.snailboard.board.service.BoardService;
import ua.comsys.kpi.snailboard.team.model.Team;
import ua.comsys.kpi.snailboard.team.service.TeamService;
import ua.comsys.kpi.snailboard.utils.Converter;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class BoardFacadeImpl implements BoardFacade {

    @Autowired
    TeamService teamService;

    @Autowired
    BoardService boardService;

    @Autowired
    Converter<Board, GetBoardByIdResponse> getBoardByIdResponseConverter;

    @Override
    public void createInitial(String name, String description, UUID teamId) {
        Team team = teamService.getTeamById(teamId);
        boardService.createInitial(name, description, team);
    }

    public GetBoardResponse getBoardsByTeam(UUID teamId) {
        Team team = teamService.getTeamById(teamId);
        List<Board> boards = boardService.getBoardsByTeam(team);
        List<BoardPreviewInfo> boardsInfo = boards.stream()
                .map(board -> new BoardPreviewInfo(board.getName(), board.getDescription()))
                .collect(Collectors.toList());
        return new GetBoardResponse(boardsInfo, team.getUsers().size());
    }

    @Override
    public GetBoardByIdResponse getBoardById(UUID boardId) {
        Board board = boardService.getBoardById(boardId);
        return getBoardByIdResponseConverter.convert(board);
    }
}
