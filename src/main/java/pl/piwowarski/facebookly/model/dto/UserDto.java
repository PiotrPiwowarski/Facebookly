package pl.piwowarski.facebookly.model.dto;

import jakarta.validation.constraints.Null;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.piwowarski.facebookly.model.enums.Gender;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {
    @Null
    private Long id;
    private String firstName;
    private String lastName;
    private Gender gender;
    private String email;
    private String login;
    private String password;
}
