package ua.comsys.kpi.snailboard.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthRequest {
    String email;
    String password;
}
