package pl.piwowarski.facebookly.model.dto.reaction;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.piwowarski.facebookly.model.enums.Reaction;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserReactionDto {

    private long id;
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    private Reaction reaction;
}
