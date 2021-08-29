package ua.comsys.kpi.snailboard.board.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateBoardRequest {
    UUID id;
    String name;
    String description;
}
