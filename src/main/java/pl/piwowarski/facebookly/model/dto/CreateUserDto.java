package pl.piwowarski.facebookly.model.dto;

import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreateUserDto {
    @Null
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String login;
    private String password;
}
