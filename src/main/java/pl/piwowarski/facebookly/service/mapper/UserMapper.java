package pl.piwowarski.facebookly.service.mapper;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.piwowarski.facebookly.model.dto.UserDto;
import pl.piwowarski.facebookly.model.entity.User;
import pl.piwowarski.facebookly.model.enums.Role;
import pl.piwowarski.facebookly.service.manager.PasswordManager;

@Service
@AllArgsConstructor
public class UserMapper implements Mapper<User, UserDto> {

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

    @Override
    public UserDto unmap(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setGender(user.getGender());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setEmail(user.getEmail());
        userDto.setLogin(user.getLogin());
        userDto.setPassword(user.getPassword());
        return userDto;
    }
}
