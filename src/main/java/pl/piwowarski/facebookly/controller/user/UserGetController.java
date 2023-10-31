package pl.piwowarski.facebookly.controller.user;

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

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long id,
                                           @RequestBody SessionDto sessionDto){
        final Set<Role> authorizedRoles = Set.of(ADMIN);
        authenticationService.authorizeAndAuthenticate(sessionDto, authorizedRoles);
        return ResponseEntity.ok(userGetService.getUserDtoById(id));
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getUsersByName(@RequestParam String firstName,
                                                       @RequestParam String lastName,
                                                       @RequestBody SessionDto sessionDto){
        final Set<Role> authorizedRoles = Set.of(USER, ADMIN);
        authenticationService.authorizeAndAuthenticate(sessionDto, authorizedRoles);
        return ResponseEntity.ok(userGetService.getUsersByUserName(firstName, lastName));
    }

    @GetMapping("all")
    public ResponseEntity<List<UserDto>> getAllUsers(@RequestBody SessionDto sessionDto){
        final Set<Role> authorizedRoles = Set.of(USER, ADMIN);
        authenticationService.authorizeAndAuthenticate(sessionDto, authorizedRoles);
        return ResponseEntity.ok(userGetService.getAllUsers());
    }

    @GetMapping("/paged")
    public ResponseEntity<List<UserDto>> getPagedUsers(@RequestParam Integer pageNumber,
                                                       @RequestParam Integer pageSize,
                                                       @RequestBody SessionDto sessionDto){
        final Set<Role> authorizedRoles = Set.of(USER, ADMIN);
        authenticationService.authorizeAndAuthenticate(sessionDto, authorizedRoles);
        return ResponseEntity.ok(userGetService.getPagedUsers(pageNumber, pageSize));
    }

    @GetMapping("{userId}/friends")
    public ResponseEntity<List<UserDto>> getUserFriends(@PathVariable Long userId,
                                                        @RequestBody SessionDto sessionDto){
        final Set<Role> authorizedRoles = Set.of(USER, ADMIN);
        authenticationService.authorizeAndAuthenticate(sessionDto, authorizedRoles);
        return ResponseEntity.ok(userGetService.getUserFriends(userId));
    }

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
