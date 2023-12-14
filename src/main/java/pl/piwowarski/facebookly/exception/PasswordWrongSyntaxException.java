package pl.piwowarski.facebookly.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PasswordWrongSyntaxException extends RuntimeException{

    private static final String MESSAGE = "Wrong syntax of password.";
    private final String message;

    public PasswordWrongSyntaxException(){
        this.message = MESSAGE;
    }
}
