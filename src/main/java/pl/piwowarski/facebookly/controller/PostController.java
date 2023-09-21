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
    public ResponseEntity<PostDto> getPostById(@PathVariable long postId){
        return ResponseEntity.ok(postService.findPostById(postId));
    }

    @GetMapping
    public ResponseEntity<List<PostDto>> getAllPosts(){
        return ResponseEntity.ok(postService.findAllPosts());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PostDto>> getAllPostsByUserId(@PathVariable Long userId){
        return ResponseEntity.ok(postService.findAllPostsByUserId(userId));
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

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePostById(@PathVariable Long postId){
        postService.deletePost(postId);
        return ResponseEntity.noContent().build();
    }
}
