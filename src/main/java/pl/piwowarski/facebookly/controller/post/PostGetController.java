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
            description = "Wymagane dane: id posta, id użytkownika, rola, token. Zwracane dane: post.")
    @GetMapping("/{postId}")
    public ResponseEntity<PostDto> getPost(@PathVariable Long postId,
                                           @RequestBody SessionDto sessionDto){
        final Set<Role> authorizedRoles = Set.of(USER, ADMIN);
        authenticationService.authorizeAndAuthenticate(sessionDto, authorizedRoles);
        return ResponseEntity.ok(postGetService.getPostDtoById(postId));
    }

    @Operation(summary = "Pobranie wszystkich postów.",
            description = "Wymagane dane: id użytkownika, rola, token. Zwracane dane: lista postów.")
    @GetMapping
    public ResponseEntity<List<PostDto>> getAllPosts(@RequestBody SessionDto sessionDto){
        final Set<Role> authorizedRoles = Set.of(USER, ADMIN);
        authenticationService.authorizeAndAuthenticate(sessionDto, authorizedRoles);
        return ResponseEntity.ok(postGetService.getAllPosts());
    }

    @Operation(summary = "Pobranie strony postów.",
            description = "Wymagane dane: numer strony, rozmiar strony, id użytkownika, rola, token. Zwracane dane: strona postów.")
    @GetMapping("/paged")
    public ResponseEntity<List<PostDto>> getPagedPosts(@RequestParam Integer pageNumber,
                                                     @RequestParam Integer pageSize,
                                                     @RequestBody SessionDto sessionDto){
        final Set<Role> authorizedRoles = Set.of(USER, ADMIN);
        authenticationService.authorizeAndAuthenticate(sessionDto, authorizedRoles);
        return ResponseEntity.ok(postGetService.getPagedPosts(pageNumber, pageSize));
    }

    @Operation(summary = "Pobranie wszystkich postów użytkownika.",
            description = "Wymagane dane: id użytkownika, rola, token. Zwracane dane: lista postów.")
    @GetMapping("/user")
    public ResponseEntity<List<PostDto>> getAllUserPosts(@RequestBody SessionDto sessionDto){
        final Set<Role> authorizedRoles = Set.of(USER, ADMIN);
        authenticationService.authorizeAndAuthenticate(sessionDto, authorizedRoles);
        return ResponseEntity.ok(postGetService.getAllUserPosts(sessionDto.getUserId()));
    }

    @Operation(summary = "Pobranie strony postów użytkownika.",
            description = "Wymagane dane: numer strony, rozmiar strony id użytkownika, rola, token. Zwracane dane: strona postów.")
    @GetMapping("/user/paged")
    public ResponseEntity<List<PostDto>> getPagedUserPosts(@RequestParam Integer pageNumber,
                                                           @RequestParam Integer pageSize,
                                                         @RequestBody SessionDto sessionDto){
        final Set<Role> authorizedRoles = Set.of(USER, ADMIN);
        authenticationService.authorizeAndAuthenticate(sessionDto, authorizedRoles);
        return ResponseEntity.ok(postGetService.getPagedUserPosts(sessionDto.getUserId(), pageNumber, pageSize));
    }

    @Operation(summary = "Pobranie wszystkich postów obserwowanych użytkowników.",
            description = "Wymagane dane: id użytkownika, rola, token. Zwracane dane: lista postów.")
    @GetMapping("/followed")
    public ResponseEntity<List<PostDto>> getAllFollowedUsersPosts(@RequestBody SessionDto sessionDto){
        final Set<Role> authorizedRoles = Set.of(USER, ADMIN);
        authenticationService.authorizeAndAuthenticate(sessionDto, authorizedRoles);
        return ResponseEntity.ok(postGetService.getFollowersPosts(sessionDto.getUserId()));
    }

    @Operation(summary = "Pobranie strony postów obserwowanych użytkowników.",
            description = "Wymagane dane: numer strony, rozmiar strony id użytkownika, rola, token. Zwracane dane: strona postów.")
    @GetMapping("/followed/paged")
    public ResponseEntity<List<PostDto>> getPagedFollowedUsersPosts(@RequestParam Integer pageNumber,
                                                                    @RequestParam Integer pageSize,
                                                                    @RequestBody SessionDto sessionDto){
        final Set<Role> authorizedRoles = Set.of(USER, ADMIN);
        authenticationService.authorizeAndAuthenticate(sessionDto, authorizedRoles);
        return ResponseEntity.ok(postGetService.getPagedFollowersPosts(sessionDto.getUserId(), pageNumber, pageSize));
    }
}
