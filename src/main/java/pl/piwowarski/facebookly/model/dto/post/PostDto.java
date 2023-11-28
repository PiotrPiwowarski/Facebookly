package pl.piwowarski.facebookly.model.dto.post;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {

    private long id;
    private String content;
    private byte[] image;
    @NotNull
    private LocalDateTime created;
    @NotNull
    private long userId;
}
