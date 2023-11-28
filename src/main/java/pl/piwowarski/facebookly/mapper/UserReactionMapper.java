package pl.piwowarski.facebookly.mapper;

import lombok.NoArgsConstructor;
import pl.piwowarski.facebookly.model.dto.reaction.UserReactionDto;
import pl.piwowarski.facebookly.model.entity.User;
import pl.piwowarski.facebookly.model.enums.Reaction;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class UserReactionMapper {

    public static UserReactionDto toDto(User user, Reaction reaction) {
		return UserReactionDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .reaction(reaction)
                .build();
    }
}
