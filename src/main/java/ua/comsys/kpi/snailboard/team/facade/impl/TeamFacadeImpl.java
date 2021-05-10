package ua.comsys.kpi.snailboard.team.facade.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.comsys.kpi.snailboard.team.facade.TeamFacade;
import ua.comsys.kpi.snailboard.team.model.Team;
import ua.comsys.kpi.snailboard.team.service.TeamService;
import ua.comsys.kpi.snailboard.user.facade.UserFacade;
import ua.comsys.kpi.snailboard.user.model.User;

import java.util.Collections;

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
}
