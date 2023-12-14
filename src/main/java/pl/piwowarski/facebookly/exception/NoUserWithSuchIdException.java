package pl.piwowarski.facebookly.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class NoUserWithSuchIdException extends RuntimeException{

    private static final String MESSAGE = "No users with the given id.";
    private final String message;

    public NoUserWithSuchIdException(){
        this.message = MESSAGE;
    }
}
