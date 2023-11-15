package pl.piwowarski.facebookly.service.user.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.piwowarski.facebookly.model.dto.user.GetUserDto;
import pl.piwowarski.facebookly.model.dto.user.UserDto;
import pl.piwowarski.facebookly.model.entity.User;
import pl.piwowarski.facebookly.service.mapper.impl.UserToGetUserDtoMapper;
import pl.piwowarski.facebookly.service.user.UserService;

@Service
@RequiredArgsConstructor
public class UserUpdateService implements UserService {

    private final UserGetService userGetService;
    private final UserToGetUserDtoMapper userToGetUserDtoMapper;

    @Transactional
    public GetUserDto updateUser(UserDto userDto){
        User user = userGetService.getUserById(userDto.getId());
        if(userDto.getFirstName() != null){
            user.setFirstName(userDto.getFirstName());
        }
        if(userDto.getLastName() != null){
            user.setLastName(userDto.getLastName());
        }
        if(userDto.getGender() != null){
            user.setGender(userDto.getGender());
        }
        if(userDto.getEmail() != null){
            user.setEmail(userDto.getEmail());
        }
        if(userDto.getPassword() != null){
            user.setPassword(userDto.getPassword());
        }
        return userToGetUserDtoMapper.map(user);
    }
}
