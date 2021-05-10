package ua.comsys.kpi.snailboard.team.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import ua.comsys.kpi.snailboard.team.dto.CreateTeamsRequest;
import ua.comsys.kpi.snailboard.team.facade.TeamFacade;

@RestController
@RequestMapping("/team")
public class TeamController {

    @Autowired
    private TeamFacade teamFacade;

    @Secured("ROLE_USER")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/createInitial")
    public void initialCreate(@RequestBody CreateTeamsRequest request) {
        teamFacade.initialCreate(request.getName(), request.getDescription());
    }
}
