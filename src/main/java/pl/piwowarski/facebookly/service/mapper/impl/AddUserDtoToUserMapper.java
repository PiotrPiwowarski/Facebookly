package pl.piwowarski.facebookly.service.mapper.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.piwowarski.facebookly.model.dto.AddUserDto;
import pl.piwowarski.facebookly.model.entity.User;
import pl.piwowarski.facebookly.service.manager.impl.PasswordManager;
import pl.piwowarski.facebookly.service.mapper.Mapper;

import static pl.piwowarski.facebookly.model.enums.Role.USER;

@Service
@RequiredArgsConstructor
public class AddUserDtoToUserMapper implements Mapper<AddUserDto, User> {

    private final PasswordManager passwordManager;

    @Override
    public User map(AddUserDto addUserDto) {
        return User
                .builder()
                .firstName(addUserDto.getFirstName())
                .lastName(addUserDto.getLastName())
                .gender(addUserDto.getGender())
                .email(addUserDto.getEmail())
                .password(passwordManager.passwordEncryption(addUserDto.getPassword()))
                .role(USER)
                .build();
    }
}
