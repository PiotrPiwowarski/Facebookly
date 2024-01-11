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
public class PostDataDto {

    private long postId;
    private String content;
    private byte[] image;
    @NotNull
    private LocalDateTime created;
    private long userId;
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    private long likes;
    private long dislikes;
}
