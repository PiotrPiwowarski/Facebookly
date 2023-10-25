package pl.piwowarski.facebookly.controller.post;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.piwowarski.facebookly.model.dto.PostDto;
import pl.piwowarski.facebookly.model.dto.SessionDto;
import pl.piwowarski.facebookly.model.dto.UserDto;
import pl.piwowarski.facebookly.model.enums.Role;
import pl.piwowarski.facebookly.service.reaction.impl.PostReactionServiceService;
import pl.piwowarski.facebookly.service.authenticator.impl.AuthenticationService;
import pl.piwowarski.facebookly.service.post.impl.PostGetService;

import java.util.List;
import java.util.Set;

import static pl.piwowarski.facebookly.model.enums.Role.ADMIN;
import static pl.piwowarski.facebookly.model.enums.Role.USER;

@RestController
@RequestMapping("posts")
@RequiredArgsConstructor
public class PostGetController {

    private final PostGetService postGetService;
    private final AuthenticationService authenticationService;

    @GetMapping("/{postId}")
    public ResponseEntity<PostDto> getPost(@PathVariable long postId,
                                           @RequestBody SessionDto sessionDto){
        final Set<Role> authorizedRoles = Set.of(USER, ADMIN);
        authenticationService.authorizeAndAuthenticate(sessionDto, authorizedRoles);
        return ResponseEntity.ok(postGetService.getPostDtoById(postId));
    }

    @GetMapping
    public ResponseEntity<List<PostDto>> getAllPosts(@RequestBody SessionDto sessionDto){
        final Set<Role> authorizedRoles = Set.of(USER, ADMIN);
        authenticationService.authorizeAndAuthenticate(sessionDto, authorizedRoles);
        return ResponseEntity.ok(postGetService.getAllPosts());
    }

    @GetMapping("/{offset}/{pageSize}")
    public ResponseEntity<List<PostDto>> getAllPosts(@PathVariable Integer offset,
                                                     @PathVariable Integer pageSize,
                                                     @RequestBody SessionDto sessionDto){
        final Set<Role> authorizedRoles = Set.of(USER, ADMIN);
        authenticationService.authorizeAndAuthenticate(sessionDto, authorizedRoles);
        return ResponseEntity.ok(postGetService.getPagedPosts(offset, pageSize));
    }

    @GetMapping("/user/{offset}/{pageSize}")
    public ResponseEntity<List<PostDto>> getAllUserPosts(@PathVariable Integer offset,
                                                         @PathVariable Integer pageSize,
                                                         @RequestBody SessionDto sessionDto){
        final Set<Role> authorizedRoles = Set.of(USER, ADMIN);
        authenticationService.authorizeAndAuthenticate(sessionDto, authorizedRoles);
        return ResponseEntity.ok(postGetService.getPagedUserPosts(sessionDto.getUserId(), offset, pageSize));
    }

    @GetMapping("/user")
    public ResponseEntity<List<PostDto>> getAllUserPosts(@RequestBody SessionDto sessionDto){
        final Set<Role> authorizedRoles = Set.of(USER, ADMIN);
        authenticationService.authorizeAndAuthenticate(sessionDto, authorizedRoles);
        return ResponseEntity.ok(postGetService.getAllUserPosts(sessionDto.getUserId()));
    }
}
