package pl.piwowarski.facebookly.controller.comment;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.piwowarski.facebookly.model.dto.comment.CommentDto;
import pl.piwowarski.facebookly.model.dto.comment.UpdateCommentDto;
import pl.piwowarski.facebookly.model.enums.Role;
import pl.piwowarski.facebookly.service.authenticator.impl.AuthenticationService;
import pl.piwowarski.facebookly.service.comment.impl.CommentUpdateService;

import java.util.Set;

import static pl.piwowarski.facebookly.model.enums.Role.ADMIN;
import static pl.piwowarski.facebookly.model.enums.Role.USER;

@RestController
@RequestMapping("comments")
@RequiredArgsConstructor
public class CommentUpdateController {

    private final CommentUpdateService commentUpdateService;
    private final AuthenticationService authenticationService;

    @Operation(summary = "Aktualizacja komentarza.",
            description = "Wymagane dane: id komentarza, content, id użytkownika, id posta, token. " +
                    "Zwracane dane: komentarz.")
    @PutMapping
    public ResponseEntity<CommentDto> updateComment(@RequestBody UpdateCommentDto updateCommentDto){
        final Set<Role> authorizedRoles = Set.of(USER, ADMIN);
        authenticationService
                .authorizeAndAuthenticate(updateCommentDto.getToken(), updateCommentDto.getUserId(), authorizedRoles);
        CommentDto comment = commentUpdateService.updateComment(updateCommentDto);
        return ResponseEntity.ok(comment);
    }
}
