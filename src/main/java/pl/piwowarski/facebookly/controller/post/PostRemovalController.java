package pl.piwowarski.facebookly.controller.post;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.piwowarski.facebookly.model.dto.SessionDto;
import pl.piwowarski.facebookly.model.enums.Role;
import pl.piwowarski.facebookly.service.authenticator.impl.AuthenticationService;
import pl.piwowarski.facebookly.service.post.impl.PostRemovalService;

import java.util.Set;

import static pl.piwowarski.facebookly.model.enums.Role.ADMIN;
import static pl.piwowarski.facebookly.model.enums.Role.USER;

@RestController
@RequestMapping("posts")
@RequiredArgsConstructor
public class PostRemovalController {

    private final PostRemovalService postRemovalService;
    private final AuthenticationService authenticationService;

    @Operation(summary = "Usunięcie posta.",
            description = "Wymagane dane: id posta, id użytkownika, rola, token. Zwracane dane: brak.")
    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable Long postId,
                                           @RequestBody SessionDto sessionDto){
        final Set<Role> authorizedRoles = Set.of(USER, ADMIN);
        authenticationService.authorizeAndAuthenticate(sessionDto, authorizedRoles);
        postRemovalService.deletePost(postId, sessionDto.getUserId(), sessionDto.getRole());
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Usunięcie wszystkich własnych postów.",
            description = "Wymagane dane: id użytkownika, rola, token. Zwracane dane: brak.")
    @DeleteMapping("/all")
    public ResponseEntity<Void> deleteAllPosts(@RequestBody SessionDto sessionDto){
        final Set<Role> authorizedRoles = Set.of(USER, ADMIN);
        authenticationService.authorizeAndAuthenticate(sessionDto, authorizedRoles);
        postRemovalService.deletePostByUserId(sessionDto.getUserId());
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Usunięcie wszystkich postów użytkownika (tylko administrator).",
            description = "Wymagane dane: id użytkownika ,id administratora, rola, token. Zwracane dane: brak.")
    @DeleteMapping("/{userId}/all")
    public ResponseEntity<Void> deleteAllUserPosts(@PathVariable Long userId, @RequestBody SessionDto sessionDto){
        final Set<Role> authorizedRoles = Set.of(ADMIN);
        authenticationService.authorizeAndAuthenticate(sessionDto, authorizedRoles);
        postRemovalService.deletePostByAdminId(userId, sessionDto.getRole());
        return ResponseEntity.noContent().build();
    }
}
