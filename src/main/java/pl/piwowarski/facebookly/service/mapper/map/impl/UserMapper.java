package pl.piwowarski.facebookly.service.mapper.map.impl;

import org.springframework.stereotype.Service;
import pl.piwowarski.facebookly.model.dto.UserDto;
import pl.piwowarski.facebookly.model.entity.User;
import pl.piwowarski.facebookly.service.mapper.map.Mapper;

@Service
public class UserMapper implements Mapper<User, UserDto> {
    @Override
    public UserDto map(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setGender(user.getGender());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setEmail(user.getEmail());
        userDto.setPassword(user.getPassword());
        return userDto;
    }
}
