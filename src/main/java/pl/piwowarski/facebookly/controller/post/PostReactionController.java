package pl.piwowarski.facebookly.controller.post;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.piwowarski.facebookly.model.dto.SessionDto;
import pl.piwowarski.facebookly.model.dto.UserReactionDto;
import pl.piwowarski.facebookly.model.entity.PostReaction;
import pl.piwowarski.facebookly.model.enums.Role;
import pl.piwowarski.facebookly.service.authenticator.impl.AuthenticationService;
import pl.piwowarski.facebookly.service.reaction.impl.PostReactionServiceService;

import java.util.List;
import java.util.Set;

import static pl.piwowarski.facebookly.model.enums.Reaction.DISLIKE;
import static pl.piwowarski.facebookly.model.enums.Reaction.LIKE;
import static pl.piwowarski.facebookly.model.enums.Role.ADMIN;
import static pl.piwowarski.facebookly.model.enums.Role.USER;

@RestController
@RequestMapping("posts/reactions")
@RequiredArgsConstructor
public class PostReactionController {

    private final AuthenticationService authenticationService;
    private final PostReactionServiceService postReactionService;

    @PostMapping("/{postId}/like")
    public ResponseEntity<Void> addLike(@PathVariable Long postId,
                                        @RequestBody SessionDto sessionDto){
        final Set<Role> authorizedRoles = Set.of(USER, ADMIN);
        authenticationService.authorizeAndAuthenticate(sessionDto, authorizedRoles);
        postReactionService.addReaction(postId, sessionDto.getUserId(), LIKE);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{postId}/dislike")
    public ResponseEntity<Void> addDislike(@PathVariable Long postId,
                                           @RequestBody SessionDto sessionDto){
        final Set<Role> authorizedRoles = Set.of(USER, ADMIN);
        authenticationService.authorizeAndAuthenticate(sessionDto, authorizedRoles);
        postReactionService.addReaction(postId, sessionDto.getUserId(), DISLIKE);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{postId}")
    public ResponseEntity<Void> deleteReaction(@PathVariable Long postId,
                                               @RequestBody SessionDto sessionDto){
        final Set<Role> authorizedRoles = Set.of(USER, ADMIN);
        authenticationService.authorizeAndAuthenticate(sessionDto, authorizedRoles);
        postReactionService.deleteReaction(postId, sessionDto.getUserId());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{postId}/likes")
    public ResponseEntity<List<UserReactionDto>> getAllPostLikes(@PathVariable Long postId,
                                                                 @RequestBody SessionDto sessionDto){
        final Set<Role> authorizedRoles = Set.of(USER, ADMIN);
        authenticationService.authorizeAndAuthenticate(sessionDto, authorizedRoles);
        return new ResponseEntity<>(postReactionService.getAllReactions(postId, LIKE), HttpStatus.OK);
    }

    @GetMapping("/{postId}/dislikes")
    public ResponseEntity<List<UserReactionDto>> getAllPostDislikes(@PathVariable Long postId,
                                                                  @RequestBody SessionDto sessionDto){
        final Set<Role> authorizedRoles = Set.of(USER, ADMIN);
        authenticationService.authorizeAndAuthenticate(sessionDto, authorizedRoles);
        return new ResponseEntity<>(postReactionService.getAllReactions(postId, DISLIKE), HttpStatus.OK);
    }
}
