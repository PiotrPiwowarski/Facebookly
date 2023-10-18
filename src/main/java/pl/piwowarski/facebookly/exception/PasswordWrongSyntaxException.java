package pl.piwowarski.facebookly.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class PasswordWrongSyntaxException extends RuntimeException{
    public static final String MESSAGE = "Niewłaściwa składnia hasła";
    private String message;
}
