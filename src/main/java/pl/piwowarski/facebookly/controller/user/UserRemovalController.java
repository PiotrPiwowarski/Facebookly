package pl.piwowarski.facebookly.controller.user;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.piwowarski.facebookly.model.dto.SessionDto;
import pl.piwowarski.facebookly.model.enums.Role;
import pl.piwowarski.facebookly.service.authenticator.impl.AuthenticationService;
import pl.piwowarski.facebookly.service.user.impl.UserRemovalService;

import java.util.Set;

import static pl.piwowarski.facebookly.model.enums.Role.ADMIN;
import static pl.piwowarski.facebookly.model.enums.Role.USER;

@RestController
@RequestMapping("users")
@RequiredArgsConstructor
public class UserRemovalController {

    private final UserRemovalService userRemovalService;
    private final AuthenticationService authenticationService;

    @Operation(summary = "Usunięcie siebie samego.",
            description = "Wymagane dane: id użytkownika, rola, token. Zwracane dane: brak.")
    @DeleteMapping
    public ResponseEntity<Void> deleteUser(@RequestBody SessionDto sessionDto){
        final Set<Role> authorizedRoles = Set.of(USER, ADMIN);
        authenticationService.authorizeAndAuthenticate(sessionDto, authorizedRoles);
        userRemovalService.deleteUser(sessionDto.getUserId(), sessionDto);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Usunięcie użytkownika (tylko administrator).",
            description = "Wymagane dane: id użytkownika, id administratora, rola, token. Zwracane dane: brak.")
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteAnotherUser(@PathVariable Long userId,
                                                  @RequestBody SessionDto sessionDto){
        final Set<Role> authorizedRoles = Set.of(ADMIN);
        authenticationService.authorizeAndAuthenticate(sessionDto, authorizedRoles);
        userRemovalService.deleteUser(userId, sessionDto);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Usunięcie użytkownika ze znajomych.",
            description = "Wymagane dane: id użytkownika, id użytkownika, rola, token. Zwracane dane: brak.")
    @DeleteMapping("friends/{friendId}")
    public ResponseEntity<Void> deleteFriend(@PathVariable Long friendId,
                                             @RequestBody SessionDto sessionDto){
        final Set<Role> authorizedRoles = Set.of(USER, ADMIN);
        authenticationService.authorizeAndAuthenticate(sessionDto, authorizedRoles);
        userRemovalService.deleteFriend(sessionDto.getUserId(), friendId);
        return ResponseEntity.noContent().build();
    }
}
