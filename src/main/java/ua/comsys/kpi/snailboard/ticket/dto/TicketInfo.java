package ua.comsys.kpi.snailboard.ticket.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.comsys.kpi.snailboard.user.dto.UserInfoDto;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketInfo {
    String name;
    String description;
    UserInfoDto reporter;
    UserInfoDto assignee;
    @JsonProperty("created_at")
    LocalDateTime createdAt;
    @JsonProperty("updated_at")
    LocalDateTime updatedAt;
    @JsonProperty("story_points")
    Integer storyPoints;
    UUID id;
    Integer position;
    @JsonProperty("ticket_number")
    Integer ticketNumber;
}
