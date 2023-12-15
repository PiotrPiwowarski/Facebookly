package pl.piwowarski.facebookly.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class WrongPasswordException extends RuntimeException {

    private static final String MESSAGE = "Wrong password.";
    private final String message;

    public WrongPasswordException(){
        this.message = MESSAGE;
    }
}
