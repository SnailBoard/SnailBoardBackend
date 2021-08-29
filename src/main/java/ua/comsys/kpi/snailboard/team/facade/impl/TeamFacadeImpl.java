package ua.comsys.kpi.snailboard.team.facade.impl;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.comsys.kpi.snailboard.email.EmailService;
import ua.comsys.kpi.snailboard.team.dto.GetTeamResponse;
import ua.comsys.kpi.snailboard.team.exception.TeamInvitationException;
import ua.comsys.kpi.snailboard.team.facade.TeamFacade;
import ua.comsys.kpi.snailboard.team.model.Team;
import ua.comsys.kpi.snailboard.team.service.TeamService;
import ua.comsys.kpi.snailboard.user.facade.UserFacade;
import ua.comsys.kpi.snailboard.user.model.User;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class TeamFacadeImpl implements TeamFacade {

    private static final String TEMPLATE_NAME = "invitation.html";
    private static final String INVITATION_SUBJECT = "Snailboard team invitation";
    private static final String INVITE_LINK_KEY = "invite_link";
    private static final String TEAM_NAME_KEY = "team_name";
    private static final String WRONG_USER = "Wrong user";

    @Autowired
    private UserFacade userFacade;

    @Autowired
    private TeamService teamService;

    @Autowired
    private EmailService emailService;

    @Override
    public void initialCreate(String name, String description, String image) {
        User currentUser = userFacade.getCurrentUserModel();
        Team team = Team.builder().name(name).description(description).
                users(Set.of(currentUser)).imageId(image).build();
        teamService.create(team);
    }

    @Override
    public void generateAndSendLink(UUID teamId, String userEmail) {
        String inviteLink = teamService.generateLink(teamId, userEmail);
        String teamName = teamService.getTeamNameById(teamId);
        Map<String, String> emailProps = new HashMap<>();
        emailProps.put(INVITE_LINK_KEY, inviteLink);
        emailProps.put(TEAM_NAME_KEY, teamName);

        emailService.sendEmail(userEmail, emailProps, TEMPLATE_NAME, INVITATION_SUBJECT);
    }

    @Override
    public void acceptTeamInvitation(UUID invitationId) {
        var teamInvite = teamService.getTeamInviteById(invitationId);
        var invitedUser = userFacade.getCurrentUserModel();
        if (!teamInvite.getInvitedEmail().equals(invitedUser.getEmail())) {
            throw new TeamInvitationException(WRONG_USER);
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
                .image(team.getImageId())
                .memberCount(team.getUsers().size())
                .build()).collect(Collectors.toList());
    }
}
