package pl.piwowarski.facebookly.service.mapper.impl;

import org.springframework.stereotype.Service;
import pl.piwowarski.facebookly.model.dto.AddUserDto;
import pl.piwowarski.facebookly.model.dto.UserDto;
import pl.piwowarski.facebookly.service.mapper.Mapper;

@Service
public class UserDtoToAddUserDtoMapper implements Mapper<UserDto, AddUserDto> {
    @Override
    public AddUserDto map(UserDto userDto) {
        AddUserDto addUserDto = new AddUserDto();
        addUserDto.setFirstName(userDto.getFirstName());
        addUserDto.setLastName(userDto.getLastName());
        addUserDto.setGender(userDto.getGender());
        addUserDto.setEmail(userDto.getEmail());
        addUserDto.setPassword(userDto.getPassword());
        return addUserDto;
    }
}
