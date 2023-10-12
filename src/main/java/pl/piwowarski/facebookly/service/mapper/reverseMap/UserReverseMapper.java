package pl.piwowarski.facebookly.service.mapper.reverseMap;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.piwowarski.facebookly.model.dto.UserDto;
import pl.piwowarski.facebookly.model.entity.User;
import pl.piwowarski.facebookly.model.enums.Role;
import pl.piwowarski.facebookly.service.manager.PasswordManager;

@Service
@AllArgsConstructor
public class UserReverseMapper implements ReverseMapper<UserDto, User>{

    private final PasswordManager passwordManager;

    @Override
    public User map(UserDto userDto) {
        User user = new User();
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setGender(userDto.getGender());
        user.setEmail(userDto.getEmail());
        user.setLogin(userDto.getLogin());
        user.setPassword(passwordManager.passwordEncryption(userDto.getPassword()));
        user.setRole(Role.USER);
        return user;
    }
}
