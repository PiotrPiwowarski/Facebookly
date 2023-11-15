package pl.piwowarski.facebookly.controller.comment;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.piwowarski.facebookly.model.dto.comment.CommentDto;
import pl.piwowarski.facebookly.service.comment.impl.CommentGetService;

import java.util.List;

@RestController
@RequestMapping("comments")
@RequiredArgsConstructor
public class CommentGetController {

    private final CommentGetService commentGetService;

    @Operation(summary = "Pobranie wszystkich komentarzy posta.",
            description = "Wymagane dane: id posta. Dane zwracane: lista komentarzy.")
    @GetMapping("/{postId}")
    public ResponseEntity<List<CommentDto>> getAllPostComments(@PathVariable Long postId){
        return ResponseEntity.ok(commentGetService.getAllCommentsByPostId(postId));
    }

    @Operation(summary = "Pobranie postronicowanych komentarzy posta.",
            description = "Wymagane dane: id posta. Dane zwracane: strona komentarzy.")
    @GetMapping("/{postId}/paged")
    public ResponseEntity<List<CommentDto>> getPagedPostComments(@PathVariable Long postId,
                                                           		@RequestParam Integer pageNumber,
                                                           		@RequestParam Integer pageSize){
        return ResponseEntity.ok(commentGetService.getAllPagedCommentsByPostId(postId, pageNumber, pageSize));
    }
}
