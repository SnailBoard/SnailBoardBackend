package ua.comsys.kpi.snailboard.user.converters;

import org.springframework.stereotype.Component;
import ua.comsys.kpi.snailboard.user.dto.UserInfoDto;
import ua.comsys.kpi.snailboard.user.model.User;
import ua.comsys.kpi.snailboard.utils.Converter;

@Component
public class UserToUserInfoConverter implements Converter<User, UserInfoDto> {
    @Override
    public UserInfoDto convert(User source) {
        return UserInfoDto.builder()
                .id(source.getId())
                .email(source.getEmail())
                .username(source.getUsername())
                .firstName(source.getFirstName()).build();
    }
}
