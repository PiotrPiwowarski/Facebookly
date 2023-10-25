package pl.piwowarski.facebookly.controller.comment;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.piwowarski.facebookly.model.dto.CommentDto;
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

    @PutMapping
    public ResponseEntity<CommentDto> updateComment(@RequestBody CommentDto commentDto){
        final Set<Role> authorizedRoles = Set.of(USER, ADMIN);
        authenticationService.authorizeAndAuthenticate(commentDto.getToken(), commentDto.getUserId(), authorizedRoles);
        CommentDto comment = commentUpdateService.updateComment(commentDto);
        return ResponseEntity.ok(comment);
    }
}
