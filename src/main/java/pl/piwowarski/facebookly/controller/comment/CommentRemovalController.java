package pl.piwowarski.facebookly.controller.comment;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.piwowarski.facebookly.model.dto.SessionDto;
import pl.piwowarski.facebookly.model.enums.Role;
import pl.piwowarski.facebookly.service.authenticator.impl.AuthenticationService;
import pl.piwowarski.facebookly.service.comment.impl.CommentRemovalService;

import java.util.Set;

import static pl.piwowarski.facebookly.model.enums.Role.ADMIN;
import static pl.piwowarski.facebookly.model.enums.Role.USER;

@RestController
@RequestMapping("comments")
@RequiredArgsConstructor
public class CommentRemovalController {

    private final CommentRemovalService commentRemovalService;
    private final AuthenticationService authenticationService;

    @Operation(summary = "Usunięcie komentarza.",
            description = "Wymagane dane: id komentarza, id użytkownika, rola, token. Zwracane dane: brak.")
    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId,
                                              @RequestBody SessionDto sessionDto) {
        final Set<Role> authorizedRoles = Set.of(USER, ADMIN);
        authenticationService.authorizeAndAuthenticate(sessionDto, authorizedRoles);
        commentRemovalService.deleteCommentById(commentId, sessionDto.getUserId(), sessionDto.getRole());
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Usunięcie wszystkich komentarzy posta (tylko twórca posta lub administrator).",
            description = "Wymagane dane: id posta, id użytkownika, rola, token. Zwracane dane: brak.")
    @DeleteMapping("/post/{postId}")
    public ResponseEntity<Void> deleteAllPostComments(@PathVariable Long postId,
                                                      @RequestBody SessionDto sessionDto){
        final Set<Role> authorizedRoles = Set.of(ADMIN);
        authenticationService.authorizeAndAuthenticate(sessionDto, authorizedRoles);
        commentRemovalService.deleteAllPostComments(postId, sessionDto.getUserId(), sessionDto.getRole());
        return ResponseEntity.noContent().build();
    }
}
