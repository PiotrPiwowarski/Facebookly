package pl.piwowarski.facebookly.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class WrongPasswordException extends RuntimeException{
    public static final String MESSAGE = "Wprowadzono błędne hasło";
    private String message;
}
