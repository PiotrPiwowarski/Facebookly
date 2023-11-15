package pl.piwowarski.facebookly.controller.comment;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.piwowarski.facebookly.model.dto.comment.CommentDto;
import pl.piwowarski.facebookly.model.dto.SessionDto;
import pl.piwowarski.facebookly.model.enums.Role;
import pl.piwowarski.facebookly.service.authenticator.impl.AuthenticationService;
import pl.piwowarski.facebookly.service.comment.impl.CommentGetService;

import java.util.List;
import java.util.Set;

import static pl.piwowarski.facebookly.model.enums.Role.ADMIN;
import static pl.piwowarski.facebookly.model.enums.Role.USER;

@RestController
@RequestMapping("comments")
@RequiredArgsConstructor
public class CommentGetController {

    private final CommentGetService commentGetService;
    private final AuthenticationService authenticationService;

    @Operation(summary = "Pobranie wszystkich komentarzy posta.",
            description = "Wymagane dane: id posta, id użytkownika, role, token. Dane zwracane: lista komentarzy.")
    @GetMapping("/{postId}")
    public ResponseEntity<List<CommentDto>> getAllPostComments(@PathVariable Long postId,
                                                               @RequestBody SessionDto sessionDto){
        final Set<Role> authorizedRoles = Set.of(USER, ADMIN);
        authenticationService.authorizeAndAuthenticate(sessionDto, authorizedRoles);
        return ResponseEntity.ok(commentGetService.getAllCommentsByPostId(postId));
    }

    @Operation(summary = "Pobranie postronicowanych komentarzy posta.",
            description = "Wymagane dane: id posta, id użytkownika, role, token. Dane zwracane: strona komentarzy.")
    @GetMapping("/{postId}/paged")
    public ResponseEntity<List<CommentDto>> getPagedPostComments(@PathVariable Long postId,
                                                           		@RequestParam Integer pageNumber,
                                                           		@RequestParam Integer pageSize,
                                                           		@RequestBody SessionDto sessionDto){
        final Set<Role> authorizedRoles = Set.of(USER, ADMIN);
        authenticationService.authorizeAndAuthenticate(sessionDto, authorizedRoles);
        return ResponseEntity.ok(commentGetService.getAllPagedCommentsByPostId(postId, pageNumber, pageSize));
    }
}
