package pl.piwowarski.facebookly.model.dto.comment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentDataDto {

    private long postId;
    private long commentId;
    private String content;
    private LocalDateTime created;
    private long userId;
    private String firstName;
    private String lastName;
    private long likes;
    private long dislikes;
}
