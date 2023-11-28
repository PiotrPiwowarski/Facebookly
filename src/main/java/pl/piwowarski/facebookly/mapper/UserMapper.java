package pl.piwowarski.facebookly.mapper;

import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.piwowarski.facebookly.model.dto.user.AddUserDto;
import pl.piwowarski.facebookly.model.dto.user.UserDto;
import pl.piwowarski.facebookly.model.entity.User;

import static lombok.AccessLevel.PRIVATE;
import static pl.piwowarski.facebookly.model.enums.Role.USER;

@NoArgsConstructor(access = PRIVATE)
public class UserMapper {

    public static User toEntity(AddUserDto addUserDto, PasswordEncoder passwordEncoder) {
        return User.builder()
                .firstName(addUserDto.getFirstName())
                .lastName(addUserDto.getLastName())
                .gender(addUserDto.getGender())
                .email(addUserDto.getEmail())
                .password(passwordEncoder.encode(addUserDto.getPassword()))
                .role(USER)
                .build();
    }

    public static UserDto toDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .gender(user.getGender())
                .email(user.getEmail())
                .build();
    }
}
