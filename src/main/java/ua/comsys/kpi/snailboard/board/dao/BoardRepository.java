package ua.comsys.kpi.snailboard.board.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.comsys.kpi.snailboard.board.model.Board;
import ua.comsys.kpi.snailboard.team.model.Team;

import java.util.List;
import java.util.UUID;

@Repository
public interface BoardRepository extends JpaRepository<Board, UUID> {
    List<Board> findAllByTeam(Team team);
}
