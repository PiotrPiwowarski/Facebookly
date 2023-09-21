package pl.piwowarski.facebookly.model.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
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
