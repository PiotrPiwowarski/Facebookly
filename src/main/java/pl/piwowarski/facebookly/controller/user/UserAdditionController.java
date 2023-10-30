package pl.piwowarski.facebookly.controller.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.piwowarski.facebookly.model.dto.AddUserDto;
import pl.piwowarski.facebookly.model.dto.SessionDto;
import pl.piwowarski.facebookly.model.dto.UserDto;
import pl.piwowarski.facebookly.model.enums.Role;
import pl.piwowarski.facebookly.service.authenticator.impl.AuthenticationService;
import pl.piwowarski.facebookly.service.user.impl.UserAdditionService;

import java.net.URI;
import java.util.Set;

import static pl.piwowarski.facebookly.model.enums.Role.ADMIN;
import static pl.piwowarski.facebookly.model.enums.Role.USER;

@RestController
@RequestMapping("users")
@RequiredArgsConstructor
public class UserAdditionController {

    private final UserAdditionService userAdditionService;
    private final AuthenticationService authenticationService;

    @PostMapping
    public ResponseEntity<Void> addUser(@RequestBody AddUserDto addUserDto){
        UserDto user = userAdditionService.addUser(addUserDto);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("posts/" + user.getId().toString())
                .buildAndExpand()
                .toUri();
        return ResponseEntity.created(uri).build();
    }

    @PostMapping("/friends/{friendId}")
    public ResponseEntity<Void> addFriend(@RequestBody SessionDto sessionDto,
                                          @PathVariable Long friendId){
        final Set<Role> authorizedRoles = Set.of(USER, ADMIN);
        authenticationService.authorizeAndAuthenticate(sessionDto, authorizedRoles);
        userAdditionService.addFriend(sessionDto.getUserId(), friendId);
        return ResponseEntity.noContent().build();
    }
}