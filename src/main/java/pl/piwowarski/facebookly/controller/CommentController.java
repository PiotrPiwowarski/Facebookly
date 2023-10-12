package pl.piwowarski.facebookly.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.piwowarski.facebookly.model.dto.CommentDto;
import pl.piwowarski.facebookly.model.dto.SessionDto;
import pl.piwowarski.facebookly.service.entityService.CommentService;
import pl.piwowarski.facebookly.service.entityService.UserService;

import java.net.URI;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/comments")
public class CommentController {

    private CommentService commentService;
    private UserService userService;

    @GetMapping("/{postId}")
    public ResponseEntity<List<CommentDto>> getAllComments(@PathVariable Long postId, @RequestBody SessionDto sessionDto){
        userService.verifyUserSession(sessionDto);
        return ResponseEntity.ok(commentService.findAllCommentsByPostId(postId));
    }

    @GetMapping("/{postId}/{offset}/{pageSize}")
    public ResponseEntity<List<CommentDto>> getAllComments(@PathVariable Long postId,
                                                           @PathVariable Integer offset,
                                                           @PathVariable Integer pageSize,
                                                           @RequestBody SessionDto sessionDto){
        userService.verifyUserSession(sessionDto);
        return ResponseEntity.ok(commentService.findAllCommentsByPostId(postId, offset, pageSize));
    }

    @PostMapping
    public ResponseEntity<Void> addComment(@RequestBody CommentDto commentDto, @RequestBody SessionDto sessionDto){
        userService.verifyUserSession(sessionDto);
        CommentDto comment = commentService.saveComment(commentDto);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("comments" + comment.getId())
                .buildAndExpand()
                .toUri();
        return ResponseEntity.created(uri).build();
    }

    @PatchMapping("/{commentId}/like")
    public ResponseEntity<Void> addLike(@PathVariable Long commentId, @RequestBody SessionDto sessionDto){
        userService.verifyUserSession(sessionDto);
        commentService.addLike(commentId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{commentId}/dislike")
    public ResponseEntity<Void> addDislike(@PathVariable Long commentId, @RequestBody SessionDto sessionDto){
        userService.verifyUserSession(sessionDto);
        commentService.addDislike(commentId);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<CommentDto> updateComment(@RequestBody CommentDto commentDto){
        userService.verifyUserSession(commentDto.getToken(), commentDto.getUserId());
        CommentDto comment = commentService.updateComment(commentDto);
        return ResponseEntity.ok(comment);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId, @RequestBody SessionDto sessionDto){
        userService.verifyUserSession(sessionDto);
        commentService.deleteById(commentId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/post/{postId}")
    public ResponseEntity<Void> deleteAllPostComments(@PathVariable Long postId, @RequestBody SessionDto sessionDto){
        userService.verifyUserSession(sessionDto);
        commentService.deleteAllPostComments(postId);
        return ResponseEntity.noContent().build();
    }
}
