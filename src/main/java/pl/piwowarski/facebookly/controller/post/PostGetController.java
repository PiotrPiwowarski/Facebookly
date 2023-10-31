package pl.piwowarski.facebookly.controller.post;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.piwowarski.facebookly.model.dto.post.PostDto;
import pl.piwowarski.facebookly.model.dto.SessionDto;
import pl.piwowarski.facebookly.model.enums.Role;
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
    public ResponseEntity<PostDto> getPost(@PathVariable Long postId,
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

    @GetMapping("/paged")
    public ResponseEntity<List<PostDto>> getPagedPosts(@RequestParam Integer pageNumber,
                                                     @RequestParam Integer pageSize,
                                                     @RequestBody SessionDto sessionDto){
        final Set<Role> authorizedRoles = Set.of(USER, ADMIN);
        authenticationService.authorizeAndAuthenticate(sessionDto, authorizedRoles);
        return ResponseEntity.ok(postGetService.getPagedPosts(pageNumber, pageSize));
    }

    @GetMapping("/user")
    public ResponseEntity<List<PostDto>> getAllUserPosts(@RequestBody SessionDto sessionDto){
        final Set<Role> authorizedRoles = Set.of(USER, ADMIN);
        authenticationService.authorizeAndAuthenticate(sessionDto, authorizedRoles);
        return ResponseEntity.ok(postGetService.getAllUserPosts(sessionDto.getUserId()));
    }

    @GetMapping("/user/paged")
    public ResponseEntity<List<PostDto>> getPagedUserPosts(@RequestParam Integer pageNumber,
                                                           @RequestParam Integer pageSize,
                                                         @RequestBody SessionDto sessionDto){
        final Set<Role> authorizedRoles = Set.of(USER, ADMIN);
        authenticationService.authorizeAndAuthenticate(sessionDto, authorizedRoles);
        return ResponseEntity.ok(postGetService.getPagedUserPosts(sessionDto.getUserId(), pageNumber, pageSize));
    }
}
