package pl.piwowarski.facebookly.service.mapper.map.impl;

import org.springframework.stereotype.Service;
import pl.piwowarski.facebookly.model.dto.user.AddUserDto;
import pl.piwowarski.facebookly.model.dto.user.UserDto;
import pl.piwowarski.facebookly.service.mapper.map.Mapper;

@Service
public class AddUserMapper implements Mapper<UserDto, AddUserDto> {
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
