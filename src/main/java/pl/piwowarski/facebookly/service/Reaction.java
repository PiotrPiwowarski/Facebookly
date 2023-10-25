package pl.piwowarski.facebookly.service;

import pl.piwowarski.facebookly.model.dto.UserDto;

import java.util.List;

public interface Reaction {

    void addLike(Long structureId, Long userId);
    void addDislike(Long structureId, Long userId);
    List<UserDto> getAllLikes(Long userId);
    List<UserDto> getAllDislikes(Long userId);
}
