package ua.comsys.kpi.snailboard.board.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.comsys.kpi.snailboard.board.dao.BoardRepository;
import ua.comsys.kpi.snailboard.board.exception.BoardNotFoundException;
import ua.comsys.kpi.snailboard.board.model.Board;
import ua.comsys.kpi.snailboard.board.service.BoardService;
import ua.comsys.kpi.snailboard.team.exception.UserNotBelongsToTeam;
import ua.comsys.kpi.snailboard.team.model.Team;
import ua.comsys.kpi.snailboard.user.facade.UserFacade;
import ua.comsys.kpi.snailboard.user.model.User;

import java.util.List;
import java.util.UUID;

@Service
public class BoardServiceImpl implements BoardService {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private UserFacade userFacade;

    @Override
    @Transactional
    public void createInitial(String name, String description, Team team) {
        validateUserBelongsToTeam(team);
        Board board = Board.builder().name(name).description(description).team(team).build();
        boardRepository.save(board);
    }

    public List<Board> getBoardsByTeam(Team team) {
        validateUserBelongsToTeam(team);
        return boardRepository.findAllByTeam(team);
    }

    @Override
    public Board getBoardById(UUID id) {
        Board board = boardRepository.findById(id).orElseThrow(BoardNotFoundException::new);
        validateUserBelongsToTeam(board.getTeam());
        return board;
    }

    private void validateUserBelongsToTeam(Team team) {
        User currentUser = userFacade.getCurrentUserModel();
        currentUser.getTeams().stream().filter(t -> t.getId().equals(team.getId())).findFirst()
                .orElseThrow(UserNotBelongsToTeam::new);
    }
}
