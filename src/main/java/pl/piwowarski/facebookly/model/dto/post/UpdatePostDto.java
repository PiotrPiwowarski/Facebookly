package pl.piwowarski.facebookly.model.dto.post;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdatePostDto {

    private String content;
    private String imagePath;
    private long userId;
}
