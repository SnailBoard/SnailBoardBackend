package ua.comsys.kpi.snailboard.team.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.comsys.kpi.snailboard.team.model.Team;
import ua.comsys.kpi.snailboard.user.model.User;

import java.util.List;
import java.util.UUID;

@Repository
public interface TeamRepository extends JpaRepository<Team, UUID> {
    List<Team> findAllByUsersIn(List<User> users);
}
