package ua.comsys.kpi.snailboard.user.dto;

import lombok.*;
import ua.comsys.kpi.snailboard.user.model.User;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserInfoDto {
    private String firstName;
    private String email;
    private String username;

    public static UserInfoDto fromEntity(User user) {
        return UserInfoDto.builder()
                .username(user.getUsername())
                .firstName(user.getFirstName())
                .email(user.getEmail())
                .build();
    }
}
