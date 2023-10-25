package pl.piwowarski.facebookly.controller.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.piwowarski.facebookly.model.dto.SessionDto;
import pl.piwowarski.facebookly.model.enums.Role;
import pl.piwowarski.facebookly.service.UserService;

import java.util.Set;

import static pl.piwowarski.facebookly.model.enums.Role.ADMIN;
import static pl.piwowarski.facebookly.model.enums.Role.USER;

@RestController
@RequestMapping("users")
@RequiredArgsConstructor
public class UserRemovalController {

    private final UserService userService;

    @DeleteMapping
    public ResponseEntity<Void> deleteUser(@RequestBody SessionDto sessionDto){
        final Set<Role> authorizedRoles = Set.of(USER, ADMIN);
        userService.authorizeAndAuthenticate(sessionDto, authorizedRoles);
        userService.deleteUser(sessionDto.getUserId(), sessionDto.getToken());
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteAnotherUser(@PathVariable Long userId,
                                                  @RequestBody SessionDto sessionDto){
        final Set<Role> authorizedRoles = Set.of(ADMIN);
        userService.authorizeAndAuthenticate(sessionDto, authorizedRoles);
        userService.deleteUser(userId, sessionDto.getToken());
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("friends/{friendId}")
    public ResponseEntity<Void> deleteFriend(@PathVariable Long friendId,
                                             @RequestBody SessionDto sessionDto){
        final Set<Role> authorizedRoles = Set.of(USER, ADMIN);
        userService.authorizeAndAuthenticate(sessionDto, authorizedRoles);
        userService.deleteFriend(sessionDto.getUserId(), friendId);
        return ResponseEntity.noContent().build();
    }
}
