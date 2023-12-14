package pl.piwowarski.facebookly.model.dto.post;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddPostDto {

    private String content;
    private String picturePath;
    private long userId;
}
