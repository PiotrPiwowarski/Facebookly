package pl.piwowarski.facebookly.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.piwowarski.facebookly.model.dto.CommentDto;
import pl.piwowarski.facebookly.service.CommentService;

import java.net.URI;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/comments")
public class CommentController {

    private CommentService commentService;

    @GetMapping("/{postId}")
    public ResponseEntity<List<CommentDto>> getAllCommentsByPostId(@PathVariable Long postId){
        return ResponseEntity.ok(commentService.findAllCommentsByPostId(postId));
    }

    @PostMapping
    public ResponseEntity<Void> addComment(@RequestBody CommentDto commentDto){
        CommentDto comment = commentService.saveComment(commentDto);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("comments" + comment.getId())
                .buildAndExpand()
                .toUri();
        return ResponseEntity.created(uri).build();
    }

    @PatchMapping("/{commentId}/like")
    public ResponseEntity<Void> addLike(@PathVariable Long commentId){
        commentService.addLike(commentId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{commentId}/dislike")
    public ResponseEntity<Void> addDislike(@PathVariable Long commentId){
		commentService.addDislike(commentId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId){
        commentService.deleteById(commentId);
        return ResponseEntity.noContent().build();
    }
}
