package ua.comsys.kpi.snailboard.token.access.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import ua.comsys.kpi.snailboard.token.dto.Token;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class AccessTokenDTO extends Token {
    private String token;
    private String secret;
}
