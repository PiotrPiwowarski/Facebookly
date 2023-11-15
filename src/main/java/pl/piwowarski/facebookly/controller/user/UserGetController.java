package pl.piwowarski.facebookly.controller.user;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.piwowarski.facebookly.model.dto.SessionDto;
import pl.piwowarski.facebookly.model.dto.user.GetUserDto;
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
            description = "Wymagane dane: id użytkownika. Zwracane dane: użytkownik.")
    @GetMapping("/{userId}")
    public ResponseEntity<GetUserDto> getUser(@PathVariable Long userId){
        return ResponseEntity.ok(userGetService.getUserDtoById(userId));
    }

    @Operation(summary = "Pobieranie użytkownika po imieniu.",
            description = "Wymagane dane: imię i nazwisko użytkownika. " +
                    "Zwracane dane: lista użytkowników.")
    @GetMapping
    public ResponseEntity<List<GetUserDto>> getUsersByName(@RequestParam String firstName,
                                                           @RequestParam String lastName){
        return ResponseEntity.ok(userGetService.getUsersByUserName(firstName, lastName));
    }

    @Operation(summary = "Pobieranie wszystkich użytkowników.",
            description = "Wymagane dane: brak. Zwracane dane: lista użytkowników.")
    @GetMapping("/all")
    public ResponseEntity<List<GetUserDto>> getAllUsers(){
        return ResponseEntity.ok(userGetService.getAllUsers());
    }

    @Operation(summary = "Pobieranie strony użytkowników.",
            description = "Wymagane dane: numer strony, rozmiar strony. " +
                    "Zwracane dane: strona użytkowników.")
    @GetMapping("/paged")
    public ResponseEntity<List<GetUserDto>> getPagedUsers(@RequestParam Integer pageNumber,
                                                          @RequestParam Integer pageSize){
        return ResponseEntity.ok(userGetService.getPagedUsers(pageNumber, pageSize));
    }

    @Operation(summary = "Pobieranie wszystkich obserwowanych użytkowników.",
            description = "Wymagane dane: id użytkownika, id użytkownika, rola, token. Zwracane dane: lista użytkowników.")
    @PostMapping("{userId}/friends")
    public ResponseEntity<List<GetUserDto>> getFollowedUsers(@PathVariable Long userId,
                                                             @RequestBody SessionDto sessionDto){
        final Set<Role> authorizedRoles = Set.of(USER, ADMIN);
        authenticationService.authorizeAndAuthenticate(sessionDto, authorizedRoles);
        return ResponseEntity.ok(userGetService.getFollowedUsers(userId));
    }

    @Operation(summary = "Pobieranie strony obserwowanych użytkowników.",
            description = "Wymagane dane: numer strony, rozmiar strony, id użytkownika, rola, token. " +
                    "Zwracane dane: strona użytkowników.")
    @PostMapping("{userId}/friends/paged")
    public ResponseEntity<List<GetUserDto>> getPagedFollowedUsers(@PathVariable Long userId,
                                                                  @RequestParam Integer pageNumber,
                                                                  @RequestParam Integer pageSize,
                                                                  @RequestBody SessionDto sessionDto){
        final Set<Role> authorizedRoles = Set.of(USER, ADMIN);
        authenticationService.authorizeAndAuthenticate(sessionDto, authorizedRoles);
        return ResponseEntity.ok(userGetService.getPagedFollowedUsers(userId, pageNumber, pageSize));
    }
}
