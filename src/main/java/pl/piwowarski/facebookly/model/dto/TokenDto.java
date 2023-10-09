package pl.piwowarski.facebookly.model.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class TokenDto {
    @NotNull
    private String token;
}
