package pl.piwowarski.facebookly.controller.user;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.piwowarski.facebookly.model.dto.SessionDto;
import pl.piwowarski.facebookly.model.dto.user.UserDto;
import pl.piwowarski.facebookly.model.enums.Role;
import pl.piwowarski.facebookly.service.authenticator.impl.AuthenticationService;
import pl.piwowarski.facebookly.service.user.impl.UserGetService;

import java.util.List;
import java.util.Set;

import static pl.piwowarski.facebookly.model.enums.Role.ADMIN;
import static pl.piwowarski.facebookly.model.enums.Role.USER;

@RestController
@RequestMapping("users")
@RequiredArgsConstructor
public class UserGetController {

    private final UserGetService userGetService;
    private final AuthenticationService authenticationService;

    @Operation(summary = "Pobieranie użytkownika po id.",
            description = "Wymagane dane: id użytkownika, id użytkownika, rola, token. Zwracane dane: użytkownik.")
    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long userId,
                                           @RequestBody SessionDto sessionDto){
        final Set<Role> authorizedRoles = Set.of(ADMIN);
        authenticationService.authorizeAndAuthenticate(sessionDto, authorizedRoles);
        return ResponseEntity.ok(userGetService.getUserDtoById(userId));
    }

    @Operation(summary = "Pobieranie użytkownika po imieniu.",
            description = "Wymagane dane: imię, nazwisko, id użytkownika, rola, token. " +
                    "Zwracane dane: lista użytkowników.")
    @GetMapping
    public ResponseEntity<List<UserDto>> getUsersByName(@RequestParam String firstName,
                                                       @RequestParam String lastName,
                                                       @RequestBody SessionDto sessionDto){
        final Set<Role> authorizedRoles = Set.of(USER, ADMIN);
        authenticationService.authorizeAndAuthenticate(sessionDto, authorizedRoles);
        return ResponseEntity.ok(userGetService.getUsersByUserName(firstName, lastName));
    }

    @Operation(summary = "Pobieranie wszystkich użytkowników.",
            description = "Wymagane dane: id użytkownika, rola, token. Zwracane dane: lista użytkowników.")
    @GetMapping("all")
    public ResponseEntity<List<UserDto>> getAllUsers(@RequestBody SessionDto sessionDto){
        final Set<Role> authorizedRoles = Set.of(USER, ADMIN);
        authenticationService.authorizeAndAuthenticate(sessionDto, authorizedRoles);
        return ResponseEntity.ok(userGetService.getAllUsers());
    }

    @Operation(summary = "Pobieranie strony użytkowników.",
            description = "Wymagane dane: numer strony, rozmiar strony, id użytkownika, rola, token. " +
                    "Zwracane dane: strona użytkowników.")
    @GetMapping("/paged")
    public ResponseEntity<List<UserDto>> getPagedUsers(@RequestParam Integer pageNumber,
                                                       @RequestParam Integer pageSize,
                                                       @RequestBody SessionDto sessionDto){
        final Set<Role> authorizedRoles = Set.of(USER, ADMIN);
        authenticationService.authorizeAndAuthenticate(sessionDto, authorizedRoles);
        return ResponseEntity.ok(userGetService.getPagedUsers(pageNumber, pageSize));
    }

    @Operation(summary = "Pobieranie wszystkich znajomych użytkownika.",
            description = "Wymagane dane: id użytkownika, id użytkownika, rola, token. Zwracane dane: lista użytkowników.")
    @GetMapping("{userId}/friends")
    public ResponseEntity<List<UserDto>> getUserFriends(@PathVariable Long userId,
                                                        @RequestBody SessionDto sessionDto){
        final Set<Role> authorizedRoles = Set.of(USER, ADMIN);
        authenticationService.authorizeAndAuthenticate(sessionDto, authorizedRoles);
        return ResponseEntity.ok(userGetService.getUserFriends(userId));
    }

    @Operation(summary = "Pobieranie strony użytkowników.",
            description = "Wymagane dane: numer strony, rozmiar strony, id użytkownika, rola, token. " +
                    "Zwracane dane: strona użytkowników.")
    @GetMapping("{userId}/friends/paged")
    public ResponseEntity<List<UserDto>> getPagedUserFriends(@PathVariable Long userId,
                                                        @RequestParam Integer pageNumber,
                                                        @RequestParam Integer pageSize,
                                                        @RequestBody SessionDto sessionDto){
        final Set<Role> authorizedRoles = Set.of(USER, ADMIN);
        authenticationService.authorizeAndAuthenticate(sessionDto, authorizedRoles);
        return ResponseEntity.ok(userGetService.getPagedUserFriends(userId, pageNumber, pageSize));
    }
}
