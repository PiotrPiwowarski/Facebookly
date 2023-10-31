package pl.piwowarski.facebookly.service.mapper.impl;

import org.springframework.stereotype.Service;
import pl.piwowarski.facebookly.model.dto.user.AddUserDto;
import pl.piwowarski.facebookly.model.dto.user.UserDto;
import pl.piwowarski.facebookly.service.mapper.Mapper;

@Service
public class UserDtoToAddUserDtoMapper implements Mapper<UserDto, AddUserDto> {

    @Override
    public AddUserDto map(UserDto userDto) {
        return AddUserDto
                .builder()
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .gender(userDto.getGender())
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .build();
    }
}
