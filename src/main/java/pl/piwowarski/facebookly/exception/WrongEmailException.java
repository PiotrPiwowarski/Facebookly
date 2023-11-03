package pl.piwowarski.facebookly.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class WrongEmailException extends RuntimeException {

    private static final String MESSAGE = "No users with given email.";
    private final String message;

    public WrongEmailException(){
        this.message = MESSAGE;
    }
}
