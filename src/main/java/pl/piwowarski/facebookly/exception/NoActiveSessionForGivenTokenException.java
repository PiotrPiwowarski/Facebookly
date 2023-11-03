package pl.piwowarski.facebookly.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class NoActiveSessionForGivenTokenException extends RuntimeException {

    private static final String MESSAGE = "There is no active session for the given token.";
    private final String message;

    public NoActiveSessionForGivenTokenException(){
        this.message = MESSAGE;
    }
}
