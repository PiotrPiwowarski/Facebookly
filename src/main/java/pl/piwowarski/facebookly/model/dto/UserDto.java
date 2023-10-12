package pl.piwowarski.facebookly.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import pl.piwowarski.facebookly.model.enums.Gender;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    @NotNull
    private Long id;
    private String firstName;
    private String lastName;
    private Gender gender;
    private String email;
    private String login;
    private String password;
    private String token;
}
