package pl.piwowarski.facebookly.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SessionDto {
    @NotNull
    private Long userId;
    @NotNull
    private String token;
}
