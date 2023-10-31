package pl.piwowarski.facebookly.service.mapper.impl;

import org.springframework.stereotype.Service;
import pl.piwowarski.facebookly.model.dto.user.UserDto;
import pl.piwowarski.facebookly.model.entity.User;
import pl.piwowarski.facebookly.service.mapper.Mapper;

@Service
public class UserToUserDtoMapper implements Mapper<User, UserDto> {

    @Override
    public UserDto map(User user) {
        return UserDto
                .builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .gender(user.getGender())
                .email(user.getEmail())
                .build();
    }
}
