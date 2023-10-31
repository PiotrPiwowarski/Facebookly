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
public class UpdateCommentDto {
    @NotNull
    private Long id;
    @NotNull
    private String content;
    @NotNull
    private Long userId;
    @NotNull
    private Long postId;
    @NotNull
    private String token;
}
