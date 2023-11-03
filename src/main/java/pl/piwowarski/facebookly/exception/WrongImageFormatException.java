package pl.piwowarski.facebookly.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class WrongImageFormatException extends RuntimeException{

    private static final String MESSAGE = "Application does not supported given format.";
    private final String message;

    public WrongImageFormatException(){
        this.message = MESSAGE;
    }
}
