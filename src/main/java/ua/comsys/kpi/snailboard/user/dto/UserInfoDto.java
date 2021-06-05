package ua.comsys.kpi.snailboard.user.dto;

import lombok.*;
import ua.comsys.kpi.snailboard.user.model.User;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserInfoDto {
    private String firstName;
    private String email;
    private String username;
    private UUID id;
}
