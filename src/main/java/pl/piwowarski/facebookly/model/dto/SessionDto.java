package pl.piwowarski.facebookly.model.dto;

import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import pl.piwowarski.facebookly.model.enums.Role;

import static jakarta.persistence.EnumType.STRING;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SessionDto {
    @NotNull
    private Long userId;
    @NotNull
    @Enumerated(STRING)
    private Role role;
    @NotNull
    private String token;
}
