package pl.piwowarski.facebookly.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LogDataDto {
    private Long userId;
    private String token;
}
