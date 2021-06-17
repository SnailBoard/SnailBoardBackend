package ua.comsys.kpi.snailboard.team.facade;

import java.util.UUID;

import ua.comsys.kpi.snailboard.team.dto.GetTeamResponse;

import java.util.List;

public interface TeamFacade {
    void initialCreate(String name, String description, String image);

    void generateAndSendLink(UUID teamId, String userEmail);

    void acceptTeamInvitation(UUID invitationId);

    List<GetTeamResponse> getTeamsForCurrentUser();
}
