package ua.comsys.kpi.snailboard.team.service;

import ua.comsys.kpi.snailboard.team.model.Team;
import ua.comsys.kpi.snailboard.team.model.TeamInvite;
import ua.comsys.kpi.snailboard.user.model.User;

import java.util.UUID;
import ua.comsys.kpi.snailboard.user.model.User;

import java.util.List;
import java.util.UUID;

public interface TeamService {
    void create(Team team);

    String generateLink(UUID teamId, String userEmail);

    String getTeamNameById(UUID teamId);

    void addUserToTeam(User invitedUser, UUID teamId);

    TeamInvite getTeamInviteById(UUID invitationId);

    void deleteTeamInvitation(TeamInvite teamInvite);

    List<Team> getTeamsByUser(User user);

    Team getTeamById(UUID uuid);
}
