package pl.piwowarski.facebookly.service.mapper.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.piwowarski.facebookly.model.dto.AddUserDto;
import pl.piwowarski.facebookly.model.entity.User;
import pl.piwowarski.facebookly.model.enums.Role;
import pl.piwowarski.facebookly.service.manager.impl.PasswordManager;
import pl.piwowarski.facebookly.service.mapper.Mapper;

@Service
@RequiredArgsConstructor
public class AddUserDtoToUserMapper implements Mapper<AddUserDto, User> {

    private final PasswordManager passwordManager;

    @Override
    public User map(AddUserDto addUserDto) {
        User user = new User();
        user.setFirstName(addUserDto.getFirstName());
        user.setLastName(addUserDto.getLastName());
        user.setGender(addUserDto.getGender());
        user.setEmail(addUserDto.getEmail());
        user.setPassword(passwordManager.passwordEncryption(addUserDto.getPassword()));
        user.setRole(Role.USER);
        return user;
    }
}
