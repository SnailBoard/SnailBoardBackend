package ua.comsys.kpi.snailboard.ticket.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.comsys.kpi.snailboard.board.model.Board;
import ua.comsys.kpi.snailboard.board.service.BoardService;
import ua.comsys.kpi.snailboard.ticket.dao.TicketNumberRepository;
import ua.comsys.kpi.snailboard.ticket.model.TicketNumber;

import java.util.Optional;
import java.util.UUID;

@Service
public class TicketNumberService {

    @Autowired
    private TicketNumberRepository ticketNumberRepository;

    @Autowired
    private BoardService boardService;

    public void incrementForBoard(UUID boardId) {
        Board board = boardService.getBoardById(boardId);
        ticketNumberRepository.findByBoard(board).ifPresentOrElse(
                this::incrementNumber,
                () -> createTicketNumber(board)
        );
    }

    private void createTicketNumber(Board board) {
        TicketNumber ticketNumber = TicketNumber.builder().board(board).number(0).build();
        ticketNumberRepository.save(ticketNumber);
    }

    private void incrementNumber(TicketNumber ticketNumber) {
        ticketNumber.setNumber(ticketNumber.getNumber() + 1);
        ticketNumberRepository.save(ticketNumber);
    }

    public TicketNumber getByBoardId(UUID boardId) {
        Board board = boardService.getBoardById(boardId);
        return ticketNumberRepository.findByBoard(board).orElseThrow(RuntimeException::new);
    }
}
