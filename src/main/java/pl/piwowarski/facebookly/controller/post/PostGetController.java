package pl.piwowarski.facebookly.controller.post;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.piwowarski.facebookly.model.dto.PostDto;
import pl.piwowarski.facebookly.model.dto.SessionDto;
import pl.piwowarski.facebookly.model.dto.UserDto;
import pl.piwowarski.facebookly.model.enums.Role;
import pl.piwowarski.facebookly.service.PostService;
import pl.piwowarski.facebookly.service.UserService;

import java.util.List;
import java.util.Set;

import static pl.piwowarski.facebookly.model.enums.Role.ADMIN;
import static pl.piwowarski.facebookly.model.enums.Role.USER;

@RestController
@RequestMapping("posts")
@RequiredArgsConstructor
public class PostGetController {

    private final PostService postService;
    private final UserService userService;

    @GetMapping("/{postId}")
    public ResponseEntity<PostDto> getPost(@PathVariable long postId,
                                           @RequestBody SessionDto sessionDto){
        final Set<Role> authorizedRoles = Set.of(USER, ADMIN);
        userService.authorizeAndAuthenticate(sessionDto, authorizedRoles);
        return ResponseEntity.ok(postService.findPostById(postId));
    }

    @GetMapping
    public ResponseEntity<List<PostDto>> getAllPosts(@RequestBody SessionDto sessionDto){
        final Set<Role> authorizedRoles = Set.of(USER, ADMIN);
        userService.authorizeAndAuthenticate(sessionDto, authorizedRoles);
        return ResponseEntity.ok(postService.findAllPosts());
    }

    @GetMapping("/{offset}/{pageSize}")
    public ResponseEntity<List<PostDto>> getAllPosts(@PathVariable Integer offset,
                                                     @PathVariable Integer pageSize,
                                                     @RequestBody SessionDto sessionDto){
        final Set<Role> authorizedRoles = Set.of(USER, ADMIN);
        userService.authorizeAndAuthenticate(sessionDto, authorizedRoles);
        return ResponseEntity.ok(postService.findAllPosts(offset, pageSize));
    }

    @GetMapping("/user/{offset}/{pageSize}")
    public ResponseEntity<List<PostDto>> getAllUserPosts(@PathVariable Integer offset,
                                                         @PathVariable Integer pageSize,
                                                         @RequestBody SessionDto sessionDto){
        final Set<Role> authorizedRoles = Set.of(USER, ADMIN);
        userService.authorizeAndAuthenticate(sessionDto, authorizedRoles);
        return ResponseEntity.ok(postService.findAllUserPosts(sessionDto.getUserId(), offset, pageSize));
    }

    @GetMapping("/user")
    public ResponseEntity<List<PostDto>> getAllUserPosts(@RequestBody SessionDto sessionDto){
        final Set<Role> authorizedRoles = Set.of(USER, ADMIN);
        userService.authorizeAndAuthenticate(sessionDto, authorizedRoles);
        return ResponseEntity.ok(postService.findAllUserPosts(sessionDto.getUserId()));
    }

    @GetMapping("/{postId}/likes")
    public ResponseEntity<List<UserDto>> getAllPostLikes(@PathVariable Long postId,
                                                         @RequestBody SessionDto sessionDto){
        final Set<Role> authorizedRoles = Set.of(USER, ADMIN);
        userService.authorizeAndAuthenticate(sessionDto, authorizedRoles);
        postService.addLike(postId, sessionDto.getUserId());
        return new ResponseEntity<>(postService.getAllDislikes(postId), HttpStatus.OK);
    }

    @GetMapping("/{postId}/dislikes")
    public ResponseEntity<List<UserDto>> getAllPostDislikes(@PathVariable Long postId,
                                                            @RequestBody SessionDto sessionDto){
        final Set<Role> authorizedRoles = Set.of(USER, ADMIN);
        userService.authorizeAndAuthenticate(sessionDto, authorizedRoles);
        postService.addDislike(postId, sessionDto.getUserId());
        return new ResponseEntity<>(postService.getAllLikes(postId), HttpStatus.OK);
    }
}
