package pl.piwowarski.facebookly.controller.authentication;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.piwowarski.facebookly.model.dto.CredentialsDto;
import pl.piwowarski.facebookly.model.dto.SessionDto;
import pl.piwowarski.facebookly.model.enums.Role;
import pl.piwowarski.facebookly.service.UserService;
import pl.piwowarski.facebookly.service.authenticator.AuthenticationService;

import java.util.Set;

import static pl.piwowarski.facebookly.model.enums.Role.ADMIN;
import static pl.piwowarski.facebookly.model.enums.Role.USER;

@RestController
@RequestMapping("/authentication")
@RequiredArgsConstructor
public class AuthenticateController {

    private final UserService userService;
    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<SessionDto> login(@RequestBody CredentialsDto credentialsDto){
        return ResponseEntity.ok(authenticationService.login(credentialsDto));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestBody SessionDto sessionDto){
        final Set<Role> authorizedRoles = Set.of(USER, ADMIN);
        userService.authorizeAndAuthenticate(sessionDto, authorizedRoles);
        authenticationService.logout(sessionDto);
        return ResponseEntity.noContent().build();
    }
}
