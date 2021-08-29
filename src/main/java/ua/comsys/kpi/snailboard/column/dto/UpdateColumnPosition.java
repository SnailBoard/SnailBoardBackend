package ua.comsys.kpi.snailboard.column.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateColumnPosition {
    UUID id;
    Integer position;
}
