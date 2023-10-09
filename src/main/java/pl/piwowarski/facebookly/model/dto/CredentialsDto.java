package pl.piwowarski.facebookly.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.piwowarski.facebookly.validator.Password;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CredentialsDto {
    @Email
    @NotNull
    private String email;
    @Password
    @NotNull
    private String password;
}
