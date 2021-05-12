package ua.comsys.kpi.snailboard.team.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetTeamResponse {
    UUID id;
    int memberCount;
    String name;
    String description;
}
