package pl.piwowarski.facebookly.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TryToFollowYourselfException extends RuntimeException {

    private static final String MESSAGE = "You cannot follow yourself.";
    private final String message;

    public TryToFollowYourselfException(){
        this.message = MESSAGE;
    }
}
