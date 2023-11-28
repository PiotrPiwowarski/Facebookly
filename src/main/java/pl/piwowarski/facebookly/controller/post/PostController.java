package pl.piwowarski.facebookly.controller.post;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.piwowarski.facebookly.model.dto.post.UpdatePostDto;
import pl.piwowarski.facebookly.model.dto.reaction.AddPostReactionDto;
import pl.piwowarski.facebookly.model.dto.reaction.UserReactionDto;
import pl.piwowarski.facebookly.model.dto.post.AddPostDto;
import pl.piwowarski.facebookly.model.dto.post.PostDto;
import pl.piwowarski.facebookly.service.post.PostService;

import java.util.List;

import static pl.piwowarski.facebookly.model.enums.Reaction.DISLIKE;
import static pl.piwowarski.facebookly.model.enums.Reaction.LIKE;

@RestController
@RequiredArgsConstructor
@RequestMapping("posts")
public class PostController {

    private final PostService postService;

    @Operation(summary = "Dodanie posta.",
            description = "Wymagane dane: id użytkownika. Zwracane dane: id posta.")
    @PostMapping
    public ResponseEntity<Long> addPost(@RequestBody AddPostDto postDto) {
        PostDto post = postService.addPost(postDto);
        return new ResponseEntity<>(post.getId(), HttpStatus.CREATED);
    }

    @Operation(summary = "Pobranie posta.",
            description = "Wymagane dane: content, ścieżka do zdjęcia, id posta. Zwracane dane: post.")
    @GetMapping("/{postId}")
    public ResponseEntity<PostDto> getPost(@PathVariable long postId) {
        PostDto postDto = postService.getPostDto(postId);
        return new ResponseEntity<>(postDto, HttpStatus.OK);
    }

    @Operation(summary = "Pobranie wszystkich postów.",
            description = "Wymagane dane: brak. Zwracane dane: lista postów.")
    @GetMapping
    public ResponseEntity<List<PostDto>> getAllPosts(){
        List<PostDto> allPosts = postService.getAllPosts();
        return new ResponseEntity<>(allPosts, HttpStatus.OK);
    }

    @Operation(summary = "Pobranie strony postów.",
            description = "Wymagane dane: numer strony, rozmiar strony. Zwracane dane: lista postów.")
    @GetMapping("/paged")
    public ResponseEntity<List<PostDto>> getPagedPosts(@RequestParam int pageNumber,
                                                       @RequestParam int pageSize) {
        List<PostDto> postsPage = postService.getPagedPosts(pageNumber, pageSize);
        return new ResponseEntity<>(postsPage, HttpStatus.OK);
    }

    @Operation(summary = "Pobranie wszystkich postów użytkownika.",
            description = "Wymagane dane: id użytkownika. Zwracane dane: lista postów.")
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PostDto>> getAllUserPosts(@PathVariable long userId) {
        List<PostDto> allUserPosts = postService.getAllUserPosts(userId);
        return new ResponseEntity<>(allUserPosts, HttpStatus.OK);
    }

    @Operation(summary = "Pobranie strony postów użytkownika.",
            description = "Wymagane dane: id użytkownika, numer strony, rozmiar strony. Zwracane dane: strona postów.")
    @GetMapping("/user/{userId}/paged")
    public ResponseEntity<List<PostDto>> getPagedUserPosts(@PathVariable long userId,
                                                           @RequestParam int pageNumber,
                                                           @RequestParam int pageSize) {
        List<PostDto> userPostsPage = postService.getPagedUserPosts(userId, pageNumber, pageSize);
        return new ResponseEntity<>(userPostsPage, HttpStatus.OK);
    }

    @Operation(summary = "Pobranie wszystkich postów obserwowanych użytkowników.",
            description = "Wymagane dane: id użytkownika. Zwracane dane: lista postów.")
    @GetMapping("/user/{userId}/followed")
    public ResponseEntity<List<PostDto>> getAllFollowedUsersPosts(@PathVariable long userId) {
        List<PostDto> followersPosts = postService.getFollowedUsersPosts(userId);
        return new ResponseEntity<>(followersPosts, HttpStatus.OK);
    }

    @Operation(summary = "Pobranie strony postów obserwowanych użytkowników.",
            description = "Wymagane dane: numer strony, rozmiar strony, id użytkownika. Zwracane dane: lista postów.")
    @GetMapping("/user/{userId}/followed/paged")
    public ResponseEntity<List<PostDto>> getPagedFollowedUsersPosts(@PathVariable long userId,
            														@RequestParam int pageNumber,
                                                                    @RequestParam int pageSize) {
        List<PostDto> pagedFollowersPosts = postService.getPagedFollowedUsersPosts(userId, pageNumber, pageSize);
        return new ResponseEntity<>(pagedFollowersPosts, HttpStatus.OK);
    }

    @Operation(summary = "Usunięcie wszystkich postów użytkownika przez administratora.",
            description = "Wymagane dane: id użytkownika. Zwracane dane: brak.")
    @DeleteMapping("/{userId}/all")
    public ResponseEntity<Void> deleteAllUserPosts(@PathVariable long userId) {
        postService.deleteAllUserPosts(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Usunięcie czyjegoś posta przez administratora.",
            description = "Wymagane dane: id posta. Zwracane dane: brak.")
    @DeleteMapping("/{postId}/user/{userId}")
    public ResponseEntity<Void> deleteUserPost(@PathVariable long postId,
                                               @PathVariable long userId) {
        postService.deletePost(postId, userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Aktualizacja posta.",
            description = "Wymagane dane: id posta, content, ścieżka do obrazka, czas stworzenia, id użytkownika. " +
                    "Zwracane dane: Post.")
    @PutMapping("/{postId}")
    public ResponseEntity<PostDto> updatePost(@PathVariable long postId,
                                              @RequestBody UpdatePostDto updatePostDto) {
        PostDto postDto = postService.updatePost(postId, updatePostDto);
        return new ResponseEntity<>(postDto, HttpStatus.OK);
    }

    @Operation(summary = "Dodanie like do posta.",
            description = "Wymagane dane: id posta, id użytkownika. Zwracane dane: brak.")
    @PostMapping("/like")
    public ResponseEntity<Void> addLike(@RequestBody AddPostReactionDto dto) {
        postService.addPostReaction(dto.getPostId(), dto.getUserId(), LIKE);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Dodanie dislike do posta.",
            description = "Wymagane dane: id posta, id użytkownika. Zwracane dane: brak.")
    @PostMapping("/dislike")
    public ResponseEntity<Void> addDislike(@RequestBody AddPostReactionDto dto) {
        postService.addPostReaction(dto.getPostId(), dto.getUserId(), DISLIKE);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Pobranie wszystkich like posta.",
            description = "Wymagane dane: id posta. Zwracane dane: lista like.")
    @GetMapping("/{postId}/likes")
    public ResponseEntity<List<UserReactionDto>> getAllPostLikes(@PathVariable long postId) {
        List<UserReactionDto> allPostLikes = postService.getAllPostReactions(postId, LIKE);
        return new ResponseEntity<>(allPostLikes, HttpStatus.OK);
    }

    @Operation(summary = "Pobranie wszystkich dislike posta.",
            description = "Wymagane dane: id posta. Zwracane dane: lista dislike.")
    @GetMapping("/{postId}/dislikes")
    public ResponseEntity<List<UserReactionDto>> getAllPostDislikes(@PathVariable long postId) {
        List<UserReactionDto> allPostDislikes = postService.getAllPostReactions(postId, DISLIKE);
        return new ResponseEntity<>(allPostDislikes, HttpStatus.OK);
    }

    @Operation(summary = "Pobranie wszystkich reakcji posta.",
            description = "Wymagane dane: id posta. Zwracane dane: lista reakcji.")
    @GetMapping("/{postId}/reactions")
    public ResponseEntity<List<UserReactionDto>> getAllPostReactions(@PathVariable long postId) {
        List<UserReactionDto> allPostReactions = postService.getAllPostReactions(postId);
        return new ResponseEntity<>(allPostReactions, HttpStatus.OK);
    }

    @Operation(summary = "Usunięcie reakcji posta.",
            description = "Wymagane dane: id posta. Zwracane dane: brak.")
    @DeleteMapping("{postId}/user/{userId}/reactions")
    public ResponseEntity<Void> deleteReaction(@PathVariable long postId,
                                               @PathVariable long userId) {
        postService.deletePostReaction(postId, userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
