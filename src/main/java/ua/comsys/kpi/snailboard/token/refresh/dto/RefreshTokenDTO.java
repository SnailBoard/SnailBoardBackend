package ua.comsys.kpi.snailboard.token.refresh.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ua.comsys.kpi.snailboard.token.dto.Token;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class RefreshTokenDTO extends Token {
    private String token;
    private String secret;
}
