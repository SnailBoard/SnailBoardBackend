package ua.comsys.kpi.snailboard.column.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.comsys.kpi.snailboard.board.model.Board;
import ua.comsys.kpi.snailboard.board.service.BoardService;
import ua.comsys.kpi.snailboard.column.dao.ColumnRepository;
import ua.comsys.kpi.snailboard.column.dto.CreateColumnRequest;
import ua.comsys.kpi.snailboard.column.dto.UpdateColumnPosition;
import ua.comsys.kpi.snailboard.column.exception.ColumnNotFoundException;
import ua.comsys.kpi.snailboard.column.exception.NotUniquePositionException;
import ua.comsys.kpi.snailboard.column.model.Columns;
import ua.comsys.kpi.snailboard.team.service.TeamService;

import java.util.HashSet;
import java.util.Set;

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

    public void updateColumnPositions(UpdateColumnPosition newColumnPosition) {
        Columns column = columnRepository.findById(newColumnPosition.getId()).orElseThrow(ColumnNotFoundException::new);
        if (column.getColumnPosition() == newColumnPosition.getPosition()) {
            return;
        }
        columnRepository.saveAll(getColumnsEligibleForUpdate(column.getBoard().getColumns(),
                column, newColumnPosition.getPosition()));
    }

    public Set<Columns> getColumnsEligibleForUpdate(Set<Columns> columns, Columns columnToUpdate, int newPosition) {
        int currentPosition = columnToUpdate.getColumnPosition();
        boolean newValueIsBigger = currentPosition <= newPosition;
        Set<Columns> columnsToUpdate = new HashSet<>();
        columns.forEach(column -> {
            int columnPosition = column.getColumnPosition();
            if (newValueIsBigger) {
                if (columnPosition > currentPosition && columnPosition <= newPosition) {
                    column.setColumnPosition(columnPosition - 1);
                    columnsToUpdate.add(column);
                }
            } else {
                if (columnPosition < currentPosition && columnPosition >= newPosition) {
                    column.setColumnPosition(columnPosition + 1);
                    columnsToUpdate.add(column);
                }
            }
        });
        columnToUpdate.setColumnPosition(newPosition);
        columnsToUpdate.add(columnToUpdate);
        return columnsToUpdate;
    }
}
