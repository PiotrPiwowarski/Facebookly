package pl.piwowarski.facebookly.model.dto.comment;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {
    private long id;
    @NotNull
    private String content;
    @NotNull
    private LocalDateTime created;
    @NotNull
    private Long userId;
    @NotNull
    private Long postId;
}
