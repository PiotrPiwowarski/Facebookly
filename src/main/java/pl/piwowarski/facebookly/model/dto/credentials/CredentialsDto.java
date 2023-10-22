package pl.piwowarski.facebookly.model.dto.credentials;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CredentialsDto {
    @Email
    @NotNull
    private String email;
    @NotNull
    private String password;
}