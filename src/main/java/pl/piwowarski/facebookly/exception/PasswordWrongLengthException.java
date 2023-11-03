package pl.piwowarski.facebookly.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PasswordWrongLengthException extends RuntimeException{

    private static final String MESSAGE = "Wrong length of password.";
    private final String message;

    public PasswordWrongLengthException(){
        this.message = MESSAGE;
    }
}
