package pl.piwowarski.facebookly.model.dto;

import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreatePostDto {
    @Null
    private Long id;
    private String content;
    private Long userId;
}
