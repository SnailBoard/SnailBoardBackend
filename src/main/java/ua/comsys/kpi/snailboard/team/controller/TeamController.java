package ua.comsys.kpi.snailboard.team.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import ua.comsys.kpi.snailboard.team.dto.CreateTeamsRequest;
import ua.comsys.kpi.snailboard.team.dto.GetTeamResponse;
import ua.comsys.kpi.snailboard.team.facade.TeamFacade;

import java.util.List;

import java.util.UUID;

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

    @Secured("ROLE_USER")
    @PostMapping(value = "/generateLink")
    public void generateAndSendInviteLinkForTeamToUser(@RequestParam UUID teamId, @RequestParam String userEmail) {
        teamFacade.generateAndSendLink(teamId, userEmail);
    }

    @Secured("ROLE_USER")
    @PutMapping("/acceptInvitation/{invitationId}")
    public void acceptInvitation(@PathVariable UUID invitationId) {
        teamFacade.acceptTeamInvitation(invitationId);
    }

}
