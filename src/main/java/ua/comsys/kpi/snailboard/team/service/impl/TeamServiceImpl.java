package ua.comsys.kpi.snailboard.team.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.comsys.kpi.snailboard.team.dao.TeamInviteRepository;
import ua.comsys.kpi.snailboard.team.dao.TeamRepository;
import ua.comsys.kpi.snailboard.team.exception.TeamInvitationException;
import ua.comsys.kpi.snailboard.team.exception.TeamNotFoundException;
import ua.comsys.kpi.snailboard.team.exception.TeamNotFoundException;
import ua.comsys.kpi.snailboard.team.exception.UserNotBelongsToTeam;
import ua.comsys.kpi.snailboard.team.model.Team;
import ua.comsys.kpi.snailboard.team.model.TeamInvite;
import ua.comsys.kpi.snailboard.team.service.TeamService;
import ua.comsys.kpi.snailboard.user.facade.UserFacade;
import ua.comsys.kpi.snailboard.user.model.User;

import java.util.*;

import ua.comsys.kpi.snailboard.user.model.User;

import java.util.UUID;

@Service
public class TeamServiceImpl implements TeamService {

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private TeamInviteRepository teamInviteRepository;

    @Autowired
    private UserFacade userFacade;

    @Override
    public void create(Team team) {
        teamRepository.save(team);
    }

    @Override
    public List<Team> getTeamsByUser(User user) {
        return teamRepository.findAllByUsersIn(Collections.singletonList(user));
    }

    @Override
    public Team getTeamById(UUID uuid) {
        return teamRepository.findById(uuid).orElseThrow(TeamNotFoundException::new);
    }

    @Override
    public String generateLink(UUID teamId, String userEmail) {
        Team team = teamRepository.findById(teamId).orElseThrow(TeamNotFoundException::new);
        validateUserBelongsToTeam(team);
        var newTeamInvite = new TeamInvite();
        newTeamInvite.setInvitedEmail(userEmail);
        newTeamInvite.setTeam(team);
        return "http://localhost:3000/team/invite/" + teamInviteRepository.save(newTeamInvite).getId().toString();
    }

    @Override
    public String getTeamNameById(UUID teamId) {
        return teamRepository.findById(teamId).map(Team::getName).orElseThrow(TeamNotFoundException::new);
    }

    @Override
    public TeamInvite getTeamInviteById(UUID invitationId) {
        return teamInviteRepository
                .findById(invitationId)
                .orElseThrow(() -> new TeamInvitationException("Invitation not found, id: " + invitationId));
    }

    @Override
    public void addUserToTeam(User invitedUser, UUID teamId) {
        var team = teamRepository.findById(teamId).map(t -> {
            t.getUsers().add(invitedUser);
            return t;
        }).orElseThrow(TeamNotFoundException::new);
        teamRepository.save(team);
    }

    @Override
    public void deleteTeamInvitation(TeamInvite teamInvite) {
        teamInviteRepository.delete(teamInvite);
    }

    public void validateUserBelongsToTeam(Team team) {
        User currentUser = userFacade.getCurrentUserModel();
        currentUser.getTeams().stream().filter(t -> t.getId().equals(team.getId())).findFirst()
                .orElseThrow(UserNotBelongsToTeam::new);
    }
}
