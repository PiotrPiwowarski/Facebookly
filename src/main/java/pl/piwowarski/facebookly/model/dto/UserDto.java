package pl.piwowarski.facebookly.model.dto;

import jakarta.validation.constraints.Null;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {
    @Null
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String login;
    private String password;
}
