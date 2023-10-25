package pl.piwowarski.facebookly.service.reaction;

import pl.piwowarski.facebookly.model.dto.UserDto;
import pl.piwowarski.facebookly.model.enums.Reaction;

import java.util.List;

public interface ReactionService<T> {

    void addReaction(Long structureId, Long userId, Reaction reaction);
    void deleteReaction(Long structureId, Long userId);
    List<T> getAllReactions(Long userId, Reaction reaction);
}
