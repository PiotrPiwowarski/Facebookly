package pl.piwowarski.facebookly.model.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {
    @Null
    private Long id;
    private String content;
    private String picturePath;
    private byte[] picture;
    @NotNull
    private LocalDateTime created;
    @NotNull
    private Long userId;
    private String token;
    private Long likes;
    private Long dislikes;
}
