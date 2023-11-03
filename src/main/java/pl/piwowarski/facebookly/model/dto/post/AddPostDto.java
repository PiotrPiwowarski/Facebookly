package pl.piwowarski.facebookly.model.dto.post;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddPostDto {
    private String content;
    private String picturePath;
    @NotNull
    private Long userId;
    @NotNull
    private String token;
}
