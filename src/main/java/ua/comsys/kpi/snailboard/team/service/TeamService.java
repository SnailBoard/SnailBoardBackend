package ua.comsys.kpi.snailboard.team.service;

import ua.comsys.kpi.snailboard.team.model.Team;
import ua.comsys.kpi.snailboard.user.model.User;

import java.util.List;
import java.util.UUID;

public interface TeamService {
    void create(Team team);

    List<Team> getTeamsByUser(User user);

    Team getTeamById(UUID uuid);
}
