package pl.piwowarski.facebookly.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.piwowarski.facebookly.model.dto.PostDto;
import pl.piwowarski.facebookly.model.dto.SessionDto;
import pl.piwowarski.facebookly.service.entityService.PostService;
import pl.piwowarski.facebookly.service.entityService.UserService;

import java.net.URI;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;
    private final UserService userService;

    @GetMapping("/{postId}")
    public ResponseEntity<PostDto> getPost(@PathVariable long postId, @RequestBody SessionDto sessionDto){
        userService.verifyUserSession(sessionDto);
        return ResponseEntity.ok(postService.findPostById(postId));
    }

    @GetMapping("/{offset}/{pageSize}")
    public ResponseEntity<List<PostDto>> getAllPosts(@PathVariable Integer offset,
                                                     @PathVariable Integer pageSize,
                                                     @RequestBody SessionDto sessionDto){
        userService.verifyUserSession(sessionDto);
        return ResponseEntity.ok(postService.findAllPosts(offset, pageSize));
    }

    @GetMapping
    public ResponseEntity<List<PostDto>> getAllPosts(@RequestBody SessionDto sessionDto){
        userService.verifyUserSession(sessionDto);
        return ResponseEntity.ok(postService.findAllPosts());
    }

    @GetMapping("/user/{offset}/{pageSize}")
    public ResponseEntity<List<PostDto>> getAllUserPosts(@PathVariable Integer offset,
                                                         @PathVariable Integer pageSize,
                                                         @RequestBody SessionDto sessionDto){
        userService.verifyUserSession(sessionDto);
        return ResponseEntity.ok(postService.findAllUserPosts(sessionDto.getUserId(), offset, pageSize));
    }

    @GetMapping("/user")
    public ResponseEntity<List<PostDto>> getAllUserPosts(@RequestBody SessionDto sessionDto){
        userService.verifyUserSession(sessionDto);
        return ResponseEntity.ok(postService.findAllUserPosts(sessionDto.getUserId()));
    }

    @PostMapping
    public ResponseEntity<Void> addPost(@RequestBody PostDto postDto){
        userService.verifyUserSession(postDto.getToken(), postDto.getUserId());
        PostDto post = postService.savePost(postDto);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("posts/" + post.getId().toString())
                .buildAndExpand()
                .toUri();
        return ResponseEntity.created(uri).build();
    }

    @PatchMapping("/{postId}/like")
    public ResponseEntity<Void> addLike(@PathVariable Long postId, @RequestBody SessionDto sessionDto){
        userService.verifyUserSession(sessionDto);
        postService.addLike(postId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{postId}/dislike")
    public ResponseEntity<Void> addDislike(@PathVariable Long postId, @RequestBody SessionDto sessionDto){
        userService.verifyUserSession(sessionDto);
        postService.addDislike(postId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{postId}")
    public ResponseEntity<PostDto> updatePost(@PathVariable Long postId, @RequestBody PostDto postDto){
        userService.verifyUserSession(postDto.getToken(), postDto.getUserId());
        PostDto post = postService.updatePost(postId, postDto);
        return ResponseEntity.ok(post);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable Long postId, @RequestBody SessionDto sessionDto){
        userService.verifyUserSession(sessionDto);
        postService.deletePost(postId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteAllUserPosts(@RequestBody SessionDto sessionDto){
        userService.verifyUserSession(sessionDto);
        postService.deleteByUserId(sessionDto.getUserId());
        return ResponseEntity.noContent().build();
    }
}
