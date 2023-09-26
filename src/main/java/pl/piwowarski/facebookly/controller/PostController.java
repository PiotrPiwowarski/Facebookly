package pl.piwowarski.facebookly.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.piwowarski.facebookly.model.dto.PostDto;
import pl.piwowarski.facebookly.service.PostService;

import java.net.URI;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    @GetMapping("/{postId}")
    public ResponseEntity<PostDto> getPost(@PathVariable long postId){
        return ResponseEntity.ok(postService.findPostById(postId));
    }

    @GetMapping("/{offset}/{pageSize}")
    public ResponseEntity<List<PostDto>> getAllPosts(@PathVariable Integer offset, @PathVariable Integer pageSize){
        return ResponseEntity.ok(postService.findAllPosts(offset, pageSize));
    }

    @GetMapping
    public ResponseEntity<List<PostDto>> getAllPosts(){
        return ResponseEntity.ok(postService.findAllPosts());
    }

    @GetMapping("/user/{userId}/{offset}/{pageSize}")
    public ResponseEntity<List<PostDto>> getAllUserPosts(@PathVariable Long userId,
                                                             @PathVariable Integer offset,
                                                             @PathVariable Integer pageSize){
        return ResponseEntity.ok(postService.findAllUserPosts(userId, offset, pageSize));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PostDto>> getAllUserPosts(@PathVariable Long userId){
        return ResponseEntity.ok(postService.findAllUserPosts(userId));
    }

    @PostMapping
    public ResponseEntity<Void> addPost(@RequestBody PostDto postDto){
        PostDto post = postService.savePost(postDto);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("posts/" + post.getId().toString())
                .buildAndExpand()
                .toUri();
        return ResponseEntity.created(uri).build();
    }

    @PatchMapping("/{postId}/like")
    public ResponseEntity<Void> addLike(@PathVariable Long postId){
        postService.addLike(postId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{postId}/dislike")
    public ResponseEntity<Void> addDislike(@PathVariable Long postId){
        postService.addDislike(postId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{postId}")
    public ResponseEntity<PostDto> updatePost(@PathVariable Long postId, @RequestBody PostDto postDto){
        PostDto post = postService.updatePost(postId, postDto);
        return ResponseEntity.ok(post);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable Long postId){
        postService.deletePost(postId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteAllUserPosts(@PathVariable Long userId){
        postService.deleteByUserId(userId);
        return ResponseEntity.noContent().build();
    }
}
