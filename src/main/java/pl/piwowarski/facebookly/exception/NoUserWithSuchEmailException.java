package pl.piwowarski.facebookly.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class NoUserWithSuchEmailException extends RuntimeException {

    private static final String MESSAGE = "No users with the given email.";
    private final String message;

    public NoUserWithSuchEmailException(){
        this.message = MESSAGE;
    }
}
