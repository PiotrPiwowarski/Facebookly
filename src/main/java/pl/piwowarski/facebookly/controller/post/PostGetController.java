package pl.piwowarski.facebookly.controller.post;

import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(summary = "Pobranie posta.",
            description = "Wymagane dane: id posta. Zwracane dane: post.")
    @GetMapping("/{postId}")
    public ResponseEntity<PostDto> getPost(@PathVariable Long postId){
        return ResponseEntity.ok(postGetService.getPostDtoById(postId));
    }

    @Operation(summary = "Pobranie wszystkich postów.",
            description = "Wymagane dane: brak. Zwracane dane: lista postów.")
    @GetMapping
    public ResponseEntity<List<PostDto>> getAllPosts(){
        return ResponseEntity.ok(postGetService.getAllPosts());
    }

    @Operation(summary = "Pobranie strony postów.",
            description = "Wymagane dane: numer strony, rozmiar strony, id użytkownika. Zwracane dane: strona postów.")
    @GetMapping("/paged")
    public ResponseEntity<List<PostDto>> getPagedPosts(@RequestParam Integer pageNumber,
                                                     @RequestParam Integer pageSize,
                                                     @RequestBody SessionDto sessionDto){
        final Set<Role> authorizedRoles = Set.of(USER, ADMIN);
        authenticationService.authorizeAndAuthenticate(sessionDto, authorizedRoles);
        return ResponseEntity.ok(postGetService.getPagedPosts(pageNumber, pageSize));
    }

    @Operation(summary = "Pobranie wszystkich postów użytkownika.",
            description = "Wymagane dane: id użytkownika. Zwracane dane: lista postów.")
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PostDto>> getAllUserPosts(@PathVariable Long userId){
        return ResponseEntity.ok(postGetService.getAllUserPosts(userId));
    }

    @Operation(summary = "Pobranie strony postów użytkownika.",
            description = "Wymagane dane: id użytkownika, numer strony, rozmiar strony. Zwracane dane: strona postów.")
    @GetMapping("/user/{userId}/paged")
    public ResponseEntity<List<PostDto>> getPagedUserPosts(@PathVariable Long userId,
                                                           @RequestParam Integer pageNumber,
                                                           @RequestParam Integer pageSize){
        return ResponseEntity.ok(postGetService.getPagedUserPosts(userId, pageNumber, pageSize));
    }

    @Operation(summary = "Pobranie wszystkich postów obserwowanych użytkowników.",
            description = "Wymagane dane: id użytkownika, rola, token. Zwracane dane: lista postów.")
    @PostMapping("/user/followed")
    public ResponseEntity<List<PostDto>> getAllFollowedUsersPosts(@RequestBody SessionDto sessionDto){
        final Set<Role> authorizedRoles = Set.of(USER, ADMIN);
        authenticationService.authorizeAndAuthenticate(sessionDto, authorizedRoles);
        return ResponseEntity.ok(postGetService.getFollowersPosts(sessionDto.getUserId()));
    }

    @Operation(summary = "Pobranie strony postów obserwowanych użytkowników.",
            description = "Wymagane dane: numer strony, rozmiar strony, id użytkownika, rola, token. Zwracane dane: strona postów.")
    @PostMapping("/user/followed/paged")
    public ResponseEntity<List<PostDto>> getPagedFollowedUsersPosts(@RequestParam Integer pageNumber,
                                                                    @RequestParam Integer pageSize,
                                                                    @RequestBody SessionDto sessionDto){
        final Set<Role> authorizedRoles = Set.of(USER, ADMIN);
        authenticationService.authorizeAndAuthenticate(sessionDto, authorizedRoles);
        return ResponseEntity.ok(postGetService.getPagedFollowersPosts(sessionDto.getUserId(), pageNumber, pageSize));
    }
}
