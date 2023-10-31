package pl.piwowarski.facebookly.service.reaction;

import pl.piwowarski.facebookly.model.dto.UserReactionDto;
import pl.piwowarski.facebookly.model.enums.Reaction;

import java.util.List;

public interface ReactionService {

    void addReaction(Long structureId, Long userId, Reaction reaction);
    void deleteReaction(Long structureId, Long userId);
    List<UserReactionDto> getAllReactions(Long userId, Reaction reaction);
}
