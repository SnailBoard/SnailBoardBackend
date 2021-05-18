package ua.comsys.kpi.snailboard.team.facade.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.comsys.kpi.snailboard.email.EmailService;
import ua.comsys.kpi.snailboard.team.exception.TeamInvitationException;
import ua.comsys.kpi.snailboard.team.dto.GetTeamResponse;
import ua.comsys.kpi.snailboard.team.facade.TeamFacade;
import ua.comsys.kpi.snailboard.team.model.Team;
import ua.comsys.kpi.snailboard.team.model.TeamInvite;
import ua.comsys.kpi.snailboard.team.service.TeamService;
import ua.comsys.kpi.snailboard.user.facade.UserFacade;
import ua.comsys.kpi.snailboard.user.model.User;
import ua.comsys.kpi.snailboard.utils.StringUtils;

import java.util.Collections;
import java.util.UUID;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class TeamFacadeImpl implements TeamFacade {

    @Autowired
    private UserFacade userFacade;

    @Autowired
    private TeamService teamService;

    @Autowired
    private EmailService emailService;

    @Override
    public void initialCreate(String name, String description) {
        User currentUser = userFacade.getCurrentUserModel();
        Team team = Team.builder().name(name).description(description).
                users(Collections.singletonList(currentUser)).build();
        teamService.create(team);
    }

    @Override
    public void generateAndSendLink(UUID teamId, String userEmail) {
        String inviteLink = teamService.generateLink(teamId, userEmail);
        String teamName = teamService.getTeamNameById(teamId);
        emailService.sendEmail(
                userEmail,
                StringUtils.prepareTeamInvitationalEmail(inviteLink, teamName),
                "Snailboard team invitation"
                );
    }

    @Override
    public void acceptTeamInvitation(UUID invitationId) {
        var teamInvite = teamService.getTeamInviteById(invitationId);
        var invitedUser = userFacade.getCurrentUserModel();
        if (!teamInvite.getInvitedEmail().equals(invitedUser.getEmail())) {
            throw new TeamInvitationException("Wrong user");
        }
        teamService.addUserToTeam(invitedUser, teamInvite.getTeam().getId());
        teamService.deleteTeamInvitation(teamInvite);
    }

    @Override
    public List<GetTeamResponse> getTeamsForCurrentUser() {
        User currentUser = userFacade.getCurrentUserModel();
        List<Team> teams = teamService.getTeamsByUser(currentUser);
        return teams.stream().map(team -> GetTeamResponse.builder()
                .id(team.getId())
                .name(team.getName())
                .description(team.getDescription())
                .memberCount(team.getUsers().size())
                .build()).collect(Collectors.toList());
    }
}
