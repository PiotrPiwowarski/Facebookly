package pl.piwowarski.facebookly.model.dto.user;

import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
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
public class UpdateUserDto {

    private String firstName;
    private String lastName;
    @Enumerated(STRING)
    private Gender gender;
    @Email
    private String email;
    private String password;
}
