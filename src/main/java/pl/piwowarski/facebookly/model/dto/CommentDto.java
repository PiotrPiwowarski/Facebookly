package pl.piwowarski.facebookly.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {
    private Long id;
    @NotNull
    private String content;
    @NotNull
    private LocalDateTime created;
    @NotNull
    private Long userId;
    @NotNull
    private Long postId;
    @NotNull
    private String token;
}
