package ua.comsys.kpi.snailboard.team.facade.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.comsys.kpi.snailboard.team.dto.GetTeamResponse;
import ua.comsys.kpi.snailboard.team.facade.TeamFacade;
import ua.comsys.kpi.snailboard.team.model.Team;
import ua.comsys.kpi.snailboard.team.service.TeamService;
import ua.comsys.kpi.snailboard.user.facade.UserFacade;
import ua.comsys.kpi.snailboard.user.model.User;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class TeamFacadeImpl implements TeamFacade {

    @Autowired
    private UserFacade userFacade;

    @Autowired
    private TeamService teamService;

    @Override
    public void initialCreate(String name, String description) {
        User currentUser = userFacade.getCurrentUserModel();
        Team team = Team.builder().naming(name).description(description).
                users(Collections.singletonList(currentUser)).build();
        teamService.create(team);
    }

    @Override
    public List<GetTeamResponse> getTeamsForCurrentUser() {
        User currentUser = userFacade.getCurrentUserModel();
        List<Team> teams = teamService.getTeamsByUser(currentUser);
        return teams.stream().map(team -> GetTeamResponse.builder()
                .id(team.getId())
                .name(team.getNaming())
                .description(team.getDescription())
                .memberCount(team.getUsers().size())
                .build()).collect(Collectors.toList());
    }
}
