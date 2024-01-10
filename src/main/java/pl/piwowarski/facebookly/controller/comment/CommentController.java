package pl.piwowarski.facebookly.controller.comment;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.piwowarski.facebookly.model.dto.comment.CommentDataDto;
import pl.piwowarski.facebookly.model.dto.post.PostDataDto;
import pl.piwowarski.facebookly.model.dto.reaction.AddCommentReactionDto;
import pl.piwowarski.facebookly.model.dto.reaction.UserReactionDto;
import pl.piwowarski.facebookly.model.dto.comment.AddCommentDto;
import pl.piwowarski.facebookly.model.dto.comment.CommentDto;
import pl.piwowarski.facebookly.model.dto.comment.UpdateCommentDto;
import pl.piwowarski.facebookly.service.comment.CommentService;
import pl.piwowarski.facebookly.service.user.UserService;

import java.util.List;

import static pl.piwowarski.facebookly.model.enums.Reaction.DISLIKE;
import static pl.piwowarski.facebookly.model.enums.Reaction.LIKE;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequiredArgsConstructor
@RequestMapping("comments")
public class CommentController {

    private final CommentService commentService;
    private final UserService userService;

    @Operation(summary = "Dodanie komentarza",
            description = "Wymagane dane: content, id użytkownika, id posta. Zwracane dane: id komentarza.")
    @PostMapping
    public ResponseEntity<Long> addComment(@RequestBody AddCommentDto addCommentDto) {
        userService.checkLoginStatus(addCommentDto.getUserId());
        CommentDto commentDto = commentService.addComment(addCommentDto);
        return new ResponseEntity<>(commentDto.getId(), HttpStatus.CREATED);
    }

    @Operation(summary = "Pobierz komentarz",
            description = "Wymagane dane: id komentarza. Zwracane dane: id komentarza.")
    @GetMapping("/{commentId}")
    public ResponseEntity<CommentDto> getComment(@PathVariable long commentId) {
        CommentDto commentDto = commentService.getComment(commentId);
        return new ResponseEntity<>(commentDto, HttpStatus.OK);
    }

    @Operation(summary = "Pobranie wszystkich komentarzy posta.",
            description = "Wymagane dane: id posta. Dane zwracane: lista komentarzy.")
    @GetMapping("/post/{postId}/all")
    public ResponseEntity<List<CommentDto>> getAllPostComments(@PathVariable long postId) {
        List<CommentDto> allPostComments = commentService.getAllPostComments(postId);
        return new ResponseEntity<>(allPostComments, HttpStatus.OK);
    }

    @Operation(summary = "Pobranie wszystkich komentarzy wraz z wymaganymi danymi.",
            description = "Wymagane dane: id posta. Zwracane dane: lista komentarzy.")
    @GetMapping("/post/{postId}/allWithData")
    public ResponseEntity<List<CommentDataDto>> getAllPostCommentsWithData(@PathVariable long postId){
        List<CommentDataDto> allPostComments = commentService.getAllPostCommentsWithData(postId);
        return new ResponseEntity<>(allPostComments, HttpStatus.OK);
    }

    @Operation(summary = "Pobranie postronicowanych komentarzy posta.",
            description = "Wymagane dane: id posta. Dane zwracane: strona komentarzy.")
    @GetMapping("/post/{postId}/paged")
    public ResponseEntity<List<CommentDto>> getPagedPostComments(@PathVariable long postId,
                                                                @RequestParam int pageNumber,
                                                                @RequestParam int pageSize) {
        List<CommentDto> pagePostComments = commentService.getPagePostComments(postId, pageNumber, pageSize);
        return new ResponseEntity<>(pagePostComments, HttpStatus.OK);
    }

    @Operation(summary = "Usunięcie komentarza.",
            description = "Wymagane dane: id komentarza, id użytkownika. Zwracane dane: brak.")
    @DeleteMapping("/{commentId}/user/{userId}")
    public ResponseEntity<Void> deleteComment(@PathVariable long commentId,
                                              @PathVariable long userId) {
        userService.checkLoginStatus(userId);
        commentService.deleteComment(commentId, userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Usunięcie wszystkich komentarzy posta.",
            description = "Wymagane dane: id posta, id użytkownika. Zwracane dane: brak.")
    @DeleteMapping("/post/{postId}/user/{userId}")
    public ResponseEntity<Void> deleteAllPostComments(@PathVariable long postId,
                                                      @PathVariable long userId) {
        userService.checkLoginStatus(userId);
        commentService.deletePostComments(postId, userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Aktualizacja komentarza.",
            description = "Wymagane dane: id komentarza, content, id użytkownika, id posta. Zwracane dane: komentarz.")
    @PutMapping("/{commentId}")
    public ResponseEntity<CommentDto> updateComment(@PathVariable long commentId,
                                                    @RequestBody UpdateCommentDto updateCommentDto) {
        userService.checkLoginStatus(updateCommentDto.getUserId());
        CommentDto commentDto = commentService.updateComment(commentId, updateCommentDto);
        return new ResponseEntity<>(commentDto, HttpStatus.OK);
    }

    @Operation(summary = "Dodanie like do komentarza.",
            description = "Wymagane dane: id komentarza, id użytkownika. Zwracane dane: id reakcji.")
    @PostMapping("/like")
    public ResponseEntity<Void> addLike(@RequestBody AddCommentReactionDto dto) {
        userService.checkLoginStatus(dto.getUserId());
        commentService.addCommentReaction(dto.getCommentId(), dto.getUserId(), LIKE);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "Dodanie dislike do komentarza.",
            description = "Wymagane dane: id komentarza, id użytkownika. Zwracane dane: id reakcji.")
    @PostMapping("/dislike")
    public ResponseEntity<Void> addDislike(@RequestBody AddCommentReactionDto dto) {
        userService.checkLoginStatus(dto.getUserId());
        commentService.addCommentReaction(dto.getCommentId(), dto.getUserId(), DISLIKE);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "Pobranie wszystkich like danego komentarza.",
            description = "Wymagane dane: id komentarza. Zwracane dane: lista like.")
    @GetMapping("/{commentId}/likes")
    public ResponseEntity<List<UserReactionDto>> getAllCommentLikes(@PathVariable long commentId) {
        List<UserReactionDto> allCommentLikes = commentService.getAllCommentReactions(commentId, LIKE);
        return new ResponseEntity<>(allCommentLikes, HttpStatus.OK);
    }

    @Operation(summary = "Pobranie liczby like'ów posta.",
            description = "Wymagane dane: id posta. Zwracane dane: ilość like'ów.")
    @GetMapping("/{commentId}/likesCount")
    public ResponseEntity<Integer> getCommentLikesCount(@PathVariable long commentId) {
        int likesCount = commentService.getReactionCount(commentId, LIKE);
        return new ResponseEntity<>(likesCount, HttpStatus.OK);
    }

    @Operation(summary = "Pobranie wszystkich dislike danego komentarza",
            description = "Dane do wprowadzenia: id komentarza. Dane zwracane: lista dislike.")
    @GetMapping("/{commentId}/dislikes")
    public ResponseEntity<List<UserReactionDto>> getAllCommentDislikes(@PathVariable long commentId) {
        List<UserReactionDto> allCommentDislikes = commentService.getAllCommentReactions(commentId, DISLIKE);
        return new ResponseEntity<>(allCommentDislikes, HttpStatus.OK);
    }

    @Operation(summary = "Pobranie liczby dislike'ów posta.",
            description = "Wymagane dane: id posta. Zwracane dane: ilość dislike'ów.")
    @GetMapping("/{commentId}/dislikesCount")
    public ResponseEntity<Integer> getCommentDislikesCount(@PathVariable long commentId) {
        int likesCount = commentService.getReactionCount(commentId, DISLIKE);
        return new ResponseEntity<>(likesCount, HttpStatus.OK);
    }

    @Operation(summary = "Pobranie wszystkich dislike danego komentarza",
            description = "Dane do wprowadzenia: id komentarza. Dane zwracane: lista reakcji.")
    @GetMapping("/{commentId}/reactions")
    public ResponseEntity<List<UserReactionDto>> getAllCommentReactions(@PathVariable long commentId) {
        List<UserReactionDto> allCommentReactions = commentService.getAllCommentReactions(commentId);
        return new ResponseEntity<>(allCommentReactions, HttpStatus.OK);
    }

    @Operation(summary = "Usunięcie reakcji na dany komentarz.",
            description = "Wymagane dane: id komentarza, id użytkownika. Zwracane dane: brak.")
    @DeleteMapping("/{commentId}/user/{userId}/reactions")
    public ResponseEntity<Void> deleteReaction(@PathVariable long commentId,
                                               @PathVariable long userId) {
        userService.checkLoginStatus(userId);
        commentService.deleteCommentReaction(commentId, userId);
        return ResponseEntity.ok().build();
    }
}
