package pl.piwowarski.facebookly.controller.comment;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.piwowarski.facebookly.model.dto.SessionDto;
import pl.piwowarski.facebookly.model.dto.UserReactionDto;
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

    @Operation(summary = "Dodanie like do komentarza.",
            description = "Wymagane dane: id komentarza, id użytkownika, rola, token. Zwracane dane: brak.")
    @PostMapping("/{commentId}/like")
    public ResponseEntity<Void> addLike(@PathVariable Long commentId,
                                        @RequestBody SessionDto sessionDto){
        final Set<Role> authorizedRoles = Set.of(USER, ADMIN);
        authenticationService.authorizeAndAuthenticate(sessionDto, authorizedRoles);
        commentReactionService.addReaction(commentId, sessionDto.getUserId(), LIKE);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Dodanie dislike do komentarza.",
            description = "Wymagane dane: id komentarza, id użytkownika, rola, token. Zwracane dane: brak.")
    @PostMapping("/{commentId}/dislike")
    public ResponseEntity<Void> addDislike(@PathVariable Long commentId,
                                           @RequestBody SessionDto sessionDto){
        final Set<Role> authorizedRoles = Set.of(USER, ADMIN);
        authenticationService.authorizeAndAuthenticate(sessionDto, authorizedRoles);
        commentReactionService.addReaction(commentId, sessionDto.getUserId(), DISLIKE);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Usunięcie reakcji na dany komentarz.",
            description = "Wymagane dane: id komentarza, id użytkownika, rola, token. Zwracane dane: brak.")
    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteReaction(@PathVariable Long commentId,
                                               @RequestBody SessionDto sessionDto){
        final Set<Role> authorizedRoles = Set.of(USER, ADMIN);
        authenticationService.authorizeAndAuthenticate(sessionDto, authorizedRoles);
        commentReactionService.deleteReaction(commentId, sessionDto.getUserId());
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Pobranie wszystkich like danego komentarza.",
            description = "Wymagane dane: id posta, id użytkownika, rola, token. Zwracane dane: lista like.")
    @GetMapping("/{postId}/likes")
    public ResponseEntity<List<UserReactionDto>> getAllCommentLikes(@PathVariable Long postId,
                                                                    @RequestBody SessionDto sessionDto){
        final Set<Role> authorizedRoles = Set.of(USER, ADMIN);
        authenticationService.authorizeAndAuthenticate(sessionDto, authorizedRoles);
        return new ResponseEntity<>(commentReactionService.getAllReactions(postId, LIKE), HttpStatus.OK);
    }

    @Operation(summary = "Pobranie wszystkich dislike danego komentarza",
            description = "Dane do wprowadzenia: id posta, id użytkownika, rola, token. Dane zwracane: lista dislike.")
    @GetMapping("/{postId}/dislikes")
    public ResponseEntity<List<UserReactionDto>> getAllCommentDislikes(@PathVariable Long postId,
                                                               @RequestBody SessionDto sessionDto) {
        final Set<Role> authorizedRoles = Set.of(USER, ADMIN);
        authenticationService.authorizeAndAuthenticate(sessionDto, authorizedRoles);
        return new ResponseEntity<>(commentReactionService.getAllReactions(postId, DISLIKE), HttpStatus.OK);
    }

    @Operation(summary = "Pobranie wszystkich dislike danego komentarza",
            description = "Dane do wprowadzenia: id posta, id użytkownika, rola, token. Dane zwracane: lista reakcji.")
    @GetMapping("/{postId}/reactions")
    public ResponseEntity<List<UserReactionDto>> getAllCommentReactions(@PathVariable Long postId,
                                                                       @RequestBody SessionDto sessionDto) {
        final Set<Role> authorizedRoles = Set.of(USER, ADMIN);
        authenticationService.authorizeAndAuthenticate(sessionDto, authorizedRoles);
        return new ResponseEntity<>(commentReactionService.getAllReactions(postId, DISLIKE), HttpStatus.OK);
    }
}
