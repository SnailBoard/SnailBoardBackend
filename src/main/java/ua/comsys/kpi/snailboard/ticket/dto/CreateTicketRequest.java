package ua.comsys.kpi.snailboard.ticket.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateTicketRequest {
    String name;
    String description;
    @JsonProperty("column_id")
    UUID columnId;
    @JsonProperty("reporter_id")
    UUID reporterId;
    @JsonProperty("assignee_id")
    UUID assigneeId;
    @JsonProperty("story_points")
    Integer storyPoints;
    @JsonProperty("column_position")
    Integer columnPosition;
}
