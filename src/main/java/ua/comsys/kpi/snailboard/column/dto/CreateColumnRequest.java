package ua.comsys.kpi.snailboard.column.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class CreateColumnRequest {
    String name;
    String description;
    UUID boardId;
    Integer columnPosition;
}
