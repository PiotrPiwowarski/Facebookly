package pl.piwowarski.facebookly.controller.comment;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.piwowarski.facebookly.model.dto.CommentDto;
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

    @GetMapping
    public ResponseEntity<List<CommentDto>> getAllPostComments(@RequestParam Long postId,
                                                           @RequestBody SessionDto sessionDto){
        final Set<Role> authorizedRoles = Set.of(USER, ADMIN);
        authenticationService.authorizeAndAuthenticate(sessionDto, authorizedRoles);
        return ResponseEntity.ok(commentGetService.getAllCommentsByPostId(postId));
    }

    @GetMapping("/paged")
    public ResponseEntity<List<CommentDto>> getPagedPostComments(@RequestParam Long postId,
                                                           @RequestParam Integer offset,
                                                           @RequestParam Integer pageSize,
                                                           @RequestBody SessionDto sessionDto){
        final Set<Role> authorizedRoles = Set.of(USER, ADMIN);
        authenticationService.authorizeAndAuthenticate(sessionDto, authorizedRoles);
        return ResponseEntity.ok(commentGetService.getAllPagedCommentsByPostId(postId, offset, pageSize));
    }
}