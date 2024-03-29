package pl.piwowarski.facebookly.controller.authentication;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.piwowarski.facebookly.model.dto.authentication.LoginDataDto;
import pl.piwowarski.facebookly.service.user.UserService;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/authentication")
@RequiredArgsConstructor
public class AuthenticationController {

    private final UserService userService;

    @Operation(summary = "Logowanie użytkownika.",
            description = "Wymagane dane: email oraz hasło. Zwracane dane: id użytkownika.")
    @PutMapping("/login")
    public ResponseEntity<Long> login(@RequestBody LoginDataDto loginDataDto){
        long userId = userService.login(loginDataDto);
        return new ResponseEntity<>(userId, HttpStatus.OK);
    }

    @Operation(summary = "Wylogowanie użytkownika.",
            description = "Wymagane dane: brak. Zwracane dane: id użytkownika.")
    @PutMapping("/{userId}/logout")
    public ResponseEntity<Long> logout(@PathVariable long userId){
        userService.logout(userId);
        return new ResponseEntity<>(userId, HttpStatus.OK);
    }
}
