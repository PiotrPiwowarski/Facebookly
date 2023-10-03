package pl.piwowarski.facebookly.model.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {
    @Null
    private Long id;
    @NotNull
    private String content;
    @NotNull
    private LocalDateTime created;
    @NotNull
    private Long userId;
    @NotNull
    private Long postId;
    private Long likes;
    private Long dislikes;
}
