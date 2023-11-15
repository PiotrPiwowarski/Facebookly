package pl.piwowarski.facebookly.controller.post;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.piwowarski.facebookly.model.dto.SessionDto;
import pl.piwowarski.facebookly.model.dto.UserReactionDto;
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

    @Operation(summary = "Dodanie like do posta.",
            description = "Wymagane dane: id posta, id użytkownika, rola, token. Zwracane dane: brak.")
    @PostMapping("/{postId}/like")
    public ResponseEntity<Void> addLike(@PathVariable Long postId,
                                        @RequestBody SessionDto sessionDto){
        final Set<Role> authorizedRoles = Set.of(USER, ADMIN);
        authenticationService.authorizeAndAuthenticate(sessionDto, authorizedRoles);
        postReactionService.addReaction(postId, sessionDto.getUserId(), LIKE);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Dodanie dislike do posta.",
            description = "Wymagane dane: id posta, id użytkownika, rola, token. Zwracane dane: brak.")
    @PostMapping("/{postId}/dislike")
    public ResponseEntity<Void> addDislike(@PathVariable Long postId,
                                           @RequestBody SessionDto sessionDto){
        final Set<Role> authorizedRoles = Set.of(USER, ADMIN);
        authenticationService.authorizeAndAuthenticate(sessionDto, authorizedRoles);
        postReactionService.addReaction(postId, sessionDto.getUserId(), DISLIKE);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Usunięcie reakcji posta.",
            description = "Wymagane dane: id posta, id użytkownika, rola, token. Zwracane dane: brak.")
    @DeleteMapping("{postId}")
    public ResponseEntity<Void> deleteReaction(@PathVariable Long postId,
                                               @RequestBody SessionDto sessionDto){
        final Set<Role> authorizedRoles = Set.of(USER, ADMIN);
        authenticationService.authorizeAndAuthenticate(sessionDto, authorizedRoles);
        postReactionService.deleteReaction(postId, sessionDto.getUserId());
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Pobranie wszystkich like posta.",
            description = "Wymagane dane: id posta. Zwracane dane: lista like.")
    @GetMapping("/{postId}/likes")
    public ResponseEntity<List<UserReactionDto>> getAllPostLikes(@PathVariable Long postId){
        return new ResponseEntity<>(postReactionService.getAllReactions(postId, LIKE), HttpStatus.OK);
    }

    @Operation(summary = "Pobranie wszystkich dislike posta.",
            description = "Wymagane dane: id posta. Zwracane dane: lista dislike.")
    @GetMapping("/{postId}/dislikes")
    public ResponseEntity<List<UserReactionDto>> getAllPostDislikes(@PathVariable Long postId){
        return new ResponseEntity<>(postReactionService.getAllReactions(postId, DISLIKE), HttpStatus.OK);
    }

    @Operation(summary = "Pobranie wszystkich reakcji posta.",
            description = "Wymagane dane: id posta. Zwracane dane: lista reakcji.")
    @GetMapping("/{postId}/reactions")
    public ResponseEntity<List<UserReactionDto>> getAllPostReactions(@PathVariable Long postId){
        return new ResponseEntity<>(postReactionService.getAllReactions(postId), HttpStatus.OK);
    }
}
