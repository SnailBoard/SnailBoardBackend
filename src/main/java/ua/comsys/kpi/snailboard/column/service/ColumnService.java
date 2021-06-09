package ua.comsys.kpi.snailboard.column.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.comsys.kpi.snailboard.board.model.Board;
import ua.comsys.kpi.snailboard.board.service.BoardService;
import ua.comsys.kpi.snailboard.column.dao.ColumnRepository;
import ua.comsys.kpi.snailboard.column.dto.CreateColumnRequest;
import ua.comsys.kpi.snailboard.column.exception.NotUniquePositionException;
import ua.comsys.kpi.snailboard.column.model.Columns;
import ua.comsys.kpi.snailboard.team.service.TeamService;

@Service
public class ColumnService {

    @Autowired
    private BoardService boardService;

    @Autowired
    private TeamService teamService;

    @Autowired
    private ColumnRepository columnRepository;

    public void createInitial(CreateColumnRequest request) {
        Board board = boardService.getBoardById(request.getBoardId());
        teamService.validateUserBelongsToTeam(board.getTeam());
        validateColumnPositionIsUniqueForBoard(board, request.getColumnPosition());
        Columns column = Columns.builder()
                .name(request.getName())
                .description(request.getDescription())
                .columnPosition(request.getColumnPosition())
                .board(board)
                .build();

        columnRepository.save(column);
    }

    private void validateColumnPositionIsUniqueForBoard(Board board, int position) {
        if (board.getColumns().stream().anyMatch(col -> col.getColumnPosition() == position)) {
            throw new NotUniquePositionException();
        }
    }
}
