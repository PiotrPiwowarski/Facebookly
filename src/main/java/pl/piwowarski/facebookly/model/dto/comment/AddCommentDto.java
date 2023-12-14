package pl.piwowarski.facebookly.model.dto.comment;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddCommentDto {
    @NotNull
    private String content;
    private long userId;
    private long postId;
}
