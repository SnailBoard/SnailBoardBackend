package ua.comsys.kpi.snailboard.ticket.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.comsys.kpi.snailboard.board.model.Board;
import ua.comsys.kpi.snailboard.ticket.model.TicketNumber;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TicketNumberRepository extends JpaRepository<TicketNumber, UUID> {
    Optional<TicketNumber> findByBoard(Board board);
}
