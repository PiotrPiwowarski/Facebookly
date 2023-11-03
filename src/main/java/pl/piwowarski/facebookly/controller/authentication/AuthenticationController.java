package pl.piwowarski.facebookly.controller.authentication;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.piwowarski.facebookly.model.dto.CredentialsDto;
import pl.piwowarski.facebookly.model.dto.SessionDto;
import pl.piwowarski.facebookly.model.enums.Role;
import pl.piwowarski.facebookly.service.authenticator.impl.AuthenticationService;

import java.util.Set;

import static pl.piwowarski.facebookly.model.enums.Role.ADMIN;
import static pl.piwowarski.facebookly.model.enums.Role.USER;

@RestController
@RequestMapping("/authentication")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @Operation(summary = "Zalogowanie użytkownika.",
            description = "Wymagane dane: email oraz hasło. Zwracane dane: id użytkownika, rola, token.")
    @PostMapping("/login")
    public ResponseEntity<SessionDto> login(@RequestBody CredentialsDto credentialsDto){
        return ResponseEntity.ok(authenticationService.login(credentialsDto));
    }

    @Operation(summary = "Wylogowanie użytkownika.",
            description = "Wymagane dane: id użytkownika, rola, token. Zwracane dane: brak.")
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestBody SessionDto sessionDto){
        final Set<Role> authorizedRoles = Set.of(USER, ADMIN);
        authenticationService.authorizeAndAuthenticate(sessionDto, authorizedRoles);
        authenticationService.logout(sessionDto);
        return ResponseEntity.noContent().build();
    }
}
