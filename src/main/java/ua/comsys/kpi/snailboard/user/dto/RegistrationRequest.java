package ua.comsys.kpi.snailboard.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationRequest {
    String email;
    String password;
    String username;
    String firstName;
}
