package pl.piwowarski.facebookly.service.user.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.piwowarski.facebookly.model.dto.SessionDto;
import pl.piwowarski.facebookly.model.entity.User;
import pl.piwowarski.facebookly.repository.UserRepository;
import pl.piwowarski.facebookly.service.authenticator.impl.AuthenticationService;
import pl.piwowarski.facebookly.service.comment.impl.CommentRemovalService;
import pl.piwowarski.facebookly.service.post.impl.PostRemovalService;
import pl.piwowarski.facebookly.service.user.UserService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserRemovalService implements UserService {

    private final UserGetService userGetService;
    private final CommentRemovalService commentRemovalService;
    private final PostRemovalService postRemovalService;
    private final AuthenticationService authenticationService;
    private final UserRepository userRepository;


    @Transactional
    public void deleteUser(Long id, SessionDto sessionDto) {
        User user = userGetService.getUserById(id);
        commentRemovalService.deleteCommentsByUserId(id);
        postRemovalService.deletePostByUserId(id);
        user.getFriends().forEach(u -> deleteFriend(u.getId(), user.getId()));
        authenticationService.logout(sessionDto);
        userRepository.deleteById(id);
    }

    @Transactional
    public void deleteFriend(Long userId, Long friendId) {
        deleteFromFriends(userId, friendId);
        deleteFromFriends(friendId, userId);
    }

    private void deleteFromFriends(Long userId, Long friendId){
        User user = userGetService.getUserById(userId);
        List<User> userFriends = user
                .getFriends()
                .stream()
                .filter(friend -> !friend.getId().equals(friendId))
                .toList();
        user.setFriends(userFriends);
    }
}
