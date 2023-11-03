package pl.piwowarski.facebookly.controller.comment;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.piwowarski.facebookly.model.dto.comment.AddCommentDto;
import pl.piwowarski.facebookly.model.dto.comment.CommentDto;
import pl.piwowarski.facebookly.model.enums.Role;
import pl.piwowarski.facebookly.service.authenticator.impl.AuthenticationService;
import pl.piwowarski.facebookly.service.comment.impl.CommentAdditionService;

import java.net.URI;
import java.util.Set;

import static pl.piwowarski.facebookly.model.enums.Role.ADMIN;
import static pl.piwowarski.facebookly.model.enums.Role.USER;

@RestController
@RequestMapping("comments")
@RequiredArgsConstructor
public class CommentAdditionController {

    private final CommentAdditionService commentAdditionService;
    private final AuthenticationService authenticationService;

    @Operation(summary = "Dodanie komentarza",
            description = "Wymagane dane: content, id użytkownika, id posta, token. Zwracane dane: brak.")
    @PostMapping
    public ResponseEntity<Void> addComment(@RequestBody AddCommentDto addCommentDto){
        final Set<Role> authorizedRoles = Set.of(USER, ADMIN);
        authenticationService.authorizeAndAuthenticate(addCommentDto.getToken(),addCommentDto.getUserId(), authorizedRoles);
        CommentDto comment = commentAdditionService.addComment(addCommentDto);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("comments" + comment.getId())
                .buildAndExpand()
                .toUri();
        return ResponseEntity.created(uri).build();
    }
}
