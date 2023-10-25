package pl.piwowarski.facebookly.controller.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.piwowarski.facebookly.model.dto.SessionDto;
import pl.piwowarski.facebookly.model.dto.UserDto;
import pl.piwowarski.facebookly.model.enums.Role;
import pl.piwowarski.facebookly.service.authenticator.AuthenticationService;
import pl.piwowarski.facebookly.service.user.GetUserService;

import java.util.List;
import java.util.Set;

import static pl.piwowarski.facebookly.model.enums.Role.ADMIN;
import static pl.piwowarski.facebookly.model.enums.Role.USER;

@RestController
@RequestMapping("users")
@RequiredArgsConstructor
public class UserGetController {

    private final GetUserService getUserService;
    private final AuthenticationService authenticationService;

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long id,
                                           @RequestBody SessionDto sessionDto){
        final Set<Role> authorizedRoles = Set.of(ADMIN);
        authenticationService.authorizeAndAuthenticate(sessionDto, authorizedRoles);
        return ResponseEntity.ok(getUserService.getUserDtoById(id));
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers(@RequestBody SessionDto sessionDto){
        final Set<Role> authorizedRoles = Set.of(USER, ADMIN);
        authenticationService.authorizeAndAuthenticate(sessionDto, authorizedRoles);
        return ResponseEntity.ok(getUserService.getAllUsers());
    }

    @GetMapping("/{pageNumber}/{pageSize}")
    public ResponseEntity<List<UserDto>> getAllUsers(@PathVariable Integer pageNumber,
                                                     @PathVariable Integer pageSize,
                                                     @RequestBody SessionDto sessionDto){
        final Set<Role> authorizedRoles = Set.of(USER, ADMIN);
        authenticationService.authorizeAndAuthenticate(sessionDto, authorizedRoles);
        return ResponseEntity.ok(getUserService.getPagedUsers(pageNumber, pageSize));
    }

    @GetMapping("/friends")
    public ResponseEntity<List<UserDto>> getUserFriends(@RequestBody SessionDto sessionDto){
        final Set<Role> authorizedRoles = Set.of(USER, ADMIN);
        authenticationService.authorizeAndAuthenticate(sessionDto, authorizedRoles);
        return ResponseEntity.ok(getUserService.getUserFriends(sessionDto.getUserId()));
    }

    @GetMapping("/friends/{pageNumber}/{pageSize}")
    public ResponseEntity<List<UserDto>> getUserFriends(@PathVariable Integer pageNumber,
                                                        @PathVariable Integer pageSize,
                                                        @RequestBody SessionDto sessionDto){
        final Set<Role> authorizedRoles = Set.of(USER, ADMIN);
        authenticationService.authorizeAndAuthenticate(sessionDto, authorizedRoles);
        return ResponseEntity.ok(getUserService.getPagedUserFriends(sessionDto.getUserId(), pageNumber, pageSize));
    }
}
