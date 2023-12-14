package pl.piwowarski.facebookly.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class NoPostWithSuchIdException extends RuntimeException{

    private static final String MESSAGE = "No posts with the given id.";
    private final String message;

    public NoPostWithSuchIdException(){
        this.message = MESSAGE;
    }
}
