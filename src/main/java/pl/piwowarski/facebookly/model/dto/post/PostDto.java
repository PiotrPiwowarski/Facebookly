package pl.piwowarski.facebookly.model.dto.post;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {
    @NotNull
    private Long id;
    private String content;
    private byte[] picture;
    @NotNull
    private LocalDateTime created;
    @NotNull
    private Long userId;
}
