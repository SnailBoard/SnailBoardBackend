package ua.comsys.kpi.snailboard.team.facade;

import ua.comsys.kpi.snailboard.team.dto.GetTeamResponse;
import ua.comsys.kpi.snailboard.team.model.Team;

import java.util.List;

public interface TeamFacade {
    void initialCreate(String name, String description);
    List<GetTeamResponse> getTeamsForCurrentUser();
}
