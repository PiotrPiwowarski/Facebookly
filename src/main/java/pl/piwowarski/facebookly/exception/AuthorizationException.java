package pl.piwowarski.facebookly.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class AuthorizationException extends RuntimeException{

    public static final String MESSAGE = "Brak uprawnień do danej operacji";
    private String message;

}
