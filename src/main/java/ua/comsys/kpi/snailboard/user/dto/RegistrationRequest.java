package ua.comsys.kpi.snailboard.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegistrationRequest {
    String email;
    String password;
    String username;
    String name;
}
