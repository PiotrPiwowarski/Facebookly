package pl.piwowarski.facebookly.model.dto;

import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.piwowarski.facebookly.model.enums.Gender;


import static jakarta.persistence.EnumType.STRING;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    @NotNull
    private Long id;
    private String firstName;
    private String lastName;
    @Enumerated(STRING)
    private Gender gender;
    @Email
    private String email;
    private String password;
    @NotNull
    private String token;
}
