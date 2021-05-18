package ua.comsys.kpi.snailboard.team.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.comsys.kpi.snailboard.team.model.TeamInvite;

import java.util.UUID;

@Repository
public interface TeamInviteRepository extends JpaRepository<TeamInvite, UUID> {
}
