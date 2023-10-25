package pl.piwowarski.facebookly.controller.comment;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.piwowarski.facebookly.model.dto.CommentDto;
import pl.piwowarski.facebookly.model.dto.SessionDto;
import pl.piwowarski.facebookly.model.dto.UserDto;
import pl.piwowarski.facebookly.model.enums.Role;
import pl.piwowarski.facebookly.service.reaction.impl.CommentReactionServiceService;
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
    private final CommentReactionServiceService commentReactionService;
    private final AuthenticationService authenticationService;

    @GetMapping("/{postId}/likes")
    public ResponseEntity<List<UserDto>> getAllCommentLikes(@PathVariable Long postId,
                                                            @RequestBody SessionDto sessionDto){
        final Set<Role> authorizedRoles = Set.of(USER, ADMIN);
        authenticationService.authorizeAndAuthenticate(sessionDto, authorizedRoles);
        return new ResponseEntity<>(commentReactionService.getAllDislikes(postId), HttpStatus.OK);
    }

    @GetMapping("/{postId}/dislikes")
    public ResponseEntity<List<UserDto>> getAllCommentDislikes(@PathVariable Long postId,
                                                               @RequestBody SessionDto sessionDto) {
        final Set<Role> authorizedRoles = Set.of(USER, ADMIN);
        authenticationService.authorizeAndAuthenticate(sessionDto, authorizedRoles);
        return new ResponseEntity<>(commentReactionService.getAllLikes(postId), HttpStatus.OK);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<List<CommentDto>> getAllComments(@PathVariable Long postId,
                                                           @RequestBody SessionDto sessionDto){
        final Set<Role> authorizedRoles = Set.of(USER, ADMIN);
        authenticationService.authorizeAndAuthenticate(sessionDto, authorizedRoles);
        return ResponseEntity.ok(commentGetService.getAllCommentsByPostId(postId));
    }

    @GetMapping("/{postId}/{offset}/{pageSize}")
    public ResponseEntity<List<CommentDto>> getAllComments(@PathVariable Long postId,
                                                           @PathVariable Integer offset,
                                                           @PathVariable Integer pageSize,
                                                           @RequestBody SessionDto sessionDto){
        final Set<Role> authorizedRoles = Set.of(USER, ADMIN);
        authenticationService.authorizeAndAuthenticate(sessionDto, authorizedRoles);
        return ResponseEntity.ok(commentGetService.getAllPagedCommentsByPostId(postId, offset, pageSize));
    }
}
