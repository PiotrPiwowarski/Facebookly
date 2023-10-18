package pl.piwowarski.facebookly.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
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
    @Email
    private String email;
    private String password;
    @NotNull
    private String token;
}
