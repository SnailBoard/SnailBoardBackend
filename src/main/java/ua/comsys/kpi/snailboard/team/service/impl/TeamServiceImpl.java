package ua.comsys.kpi.snailboard.team.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.comsys.kpi.snailboard.team.dao.TeamRepository;
import ua.comsys.kpi.snailboard.team.model.Team;
import ua.comsys.kpi.snailboard.team.service.TeamService;
import ua.comsys.kpi.snailboard.user.model.User;

import java.util.Collections;
import java.util.List;

@Service
public class TeamServiceImpl implements TeamService {

    @Autowired
    private TeamRepository teamRepository;

    @Override
    public void create(Team team) {
        teamRepository.save(team);
    }

    @Override
    public List<Team> getTeamsByUser(User user) {
        return teamRepository.findAllByUsersIn(Collections.singletonList(user));
    }
}
