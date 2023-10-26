package pl.piwowarski.facebookly.controller.comment;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.piwowarski.facebookly.model.dto.SessionDto;
import pl.piwowarski.facebookly.model.entity.CommentReaction;
import pl.piwowarski.facebookly.model.enums.Role;
import pl.piwowarski.facebookly.service.authenticator.impl.AuthenticationService;
import pl.piwowarski.facebookly.service.reaction.impl.CommentReactionService;

import java.util.List;
import java.util.Set;

import static pl.piwowarski.facebookly.model.enums.Reaction.DISLIKE;
import static pl.piwowarski.facebookly.model.enums.Reaction.LIKE;
import static pl.piwowarski.facebookly.model.enums.Role.ADMIN;
import static pl.piwowarski.facebookly.model.enums.Role.USER;

@RestController
@RequestMapping("comments/reactions")
@RequiredArgsConstructor
public class CommentReactionController {

    private final AuthenticationService authenticationService;
    private final CommentReactionService commentReactionService;

    @PostMapping("/{commentId}/like")
    public ResponseEntity<Void> addLike(@PathVariable Long commentId,
                                        @RequestBody SessionDto sessionDto){
        final Set<Role> authorizedRoles = Set.of(USER, ADMIN);
        authenticationService.authorizeAndAuthenticate(sessionDto, authorizedRoles);
        commentReactionService.addReaction(commentId, sessionDto.getUserId(), LIKE);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{commentId}/dislike")
    public ResponseEntity<Void> addDislike(@PathVariable Long commentId,
                                           @RequestBody SessionDto sessionDto){
        final Set<Role> authorizedRoles = Set.of(USER, ADMIN);
        authenticationService.authorizeAndAuthenticate(sessionDto, authorizedRoles);
        commentReactionService.addReaction(commentId, sessionDto.getUserId(), DISLIKE);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteReaction(@PathVariable Long commentId,
                                               @RequestBody SessionDto sessionDto){
        final Set<Role> authorizedRoles = Set.of(USER, ADMIN);
        authenticationService.authorizeAndAuthenticate(sessionDto, authorizedRoles);
        commentReactionService.deleteReaction(commentId, sessionDto.getUserId());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{postId}/likes")
    public ResponseEntity<List<CommentReaction>> getAllCommentLikes(@PathVariable Long postId,
                                                                    @RequestBody SessionDto sessionDto){
        final Set<Role> authorizedRoles = Set.of(USER, ADMIN);
        authenticationService.authorizeAndAuthenticate(sessionDto, authorizedRoles);
        return new ResponseEntity<>(commentReactionService.getAllReactions(postId, LIKE), HttpStatus.OK);
    }

    @GetMapping("/{postId}/dislikes")
    public ResponseEntity<List<CommentReaction>> getAllCommentDislikes(@PathVariable Long postId,
                                                               @RequestBody SessionDto sessionDto) {
        final Set<Role> authorizedRoles = Set.of(USER, ADMIN);
        authenticationService.authorizeAndAuthenticate(sessionDto, authorizedRoles);
        return new ResponseEntity<>(commentReactionService.getAllReactions(postId, DISLIKE), HttpStatus.OK);
    }
}
