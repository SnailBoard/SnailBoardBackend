package ua.comsys.kpi.snailboard.team.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import ua.comsys.kpi.snailboard.team.dto.CreateTeamsRequest;
import ua.comsys.kpi.snailboard.team.dto.GetTeamResponse;
import ua.comsys.kpi.snailboard.team.facade.TeamFacade;

import java.util.List;

@RestController
@RequestMapping("/team")
public class TeamController {

    @Autowired
    private TeamFacade teamFacade;

    @Secured("ROLE_USER")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void initialCreate(@RequestBody CreateTeamsRequest request) {
        teamFacade.initialCreate(request.getName(), request.getDescription());
    }

    @Secured("ROLE_USER")
    @GetMapping
    public List<GetTeamResponse> getUsers() {
        return teamFacade.getTeamsForCurrentUser();
    }
}
