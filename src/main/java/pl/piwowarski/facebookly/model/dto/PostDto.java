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
public class PostDto {
    @Null
    private Long id;
    @NotNull
    private String content;
    @NotNull
    private LocalDateTime created;
    @NotNull
    private Long userId;
    private Long likes;
    private Long dislikes;
}
