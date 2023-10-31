package pl.piwowarski.facebookly.controller.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.piwowarski.facebookly.model.dto.user.UserDto;
import pl.piwowarski.facebookly.model.enums.Role;
import pl.piwowarski.facebookly.service.authenticator.impl.AuthenticationService;
import pl.piwowarski.facebookly.service.user.impl.UserUpdateService;

import java.util.Set;

import static pl.piwowarski.facebookly.model.enums.Role.ADMIN;
import static pl.piwowarski.facebookly.model.enums.Role.USER;

@RestController
@RequestMapping("users")
@RequiredArgsConstructor
public class UserUpdateController {

    private final UserUpdateService userUpdateService;
    private final AuthenticationService authenticationService;

    @PutMapping
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto){
        final Set<Role> authorizedRoles = Set.of(USER, ADMIN);
        authenticationService.authorizeAndAuthenticate(userDto.getToken(), userDto.getId(), authorizedRoles);
        UserDto user = userUpdateService.updateUser(userDto);
        return ResponseEntity.ok(user);
    }
}
