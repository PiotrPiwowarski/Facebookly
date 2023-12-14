package pl.piwowarski.facebookly.controller.authentication;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.piwowarski.facebookly.model.dto.authentication.UsernamePasswordDto;

@RestController
@RequestMapping("/authentication")
@RequiredArgsConstructor
public class AuthenticationController {

    @Operation(summary = "Zalogowanie użytkownika.",
            description = "Wymagane dane: email oraz hasło. Zwracane dane: id użytkownika, rola, token.")
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UsernamePasswordDto usernamePasswordDto){
        return new ResponseEntity<>("Jesteś zalogowany", HttpStatus.OK);
    }

    @Operation(summary = "Zalogowanie użytkownika.",
            description = "Wymagane dane: email oraz hasło. Zwracane dane: id użytkownika, rola, token.")
    @PostMapping("/logout")
    public ResponseEntity<String> logout(){
        return new ResponseEntity<>("Jesteś wylogowany", HttpStatus.OK);
    }
}
