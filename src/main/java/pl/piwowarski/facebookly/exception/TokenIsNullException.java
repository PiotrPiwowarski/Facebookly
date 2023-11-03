package pl.piwowarski.facebookly.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TokenIsNullException extends RuntimeException{

    private static final String MESSAGE = "Token is null.";
    private final String message;

    public TokenIsNullException(){
        this.message = MESSAGE;
    }
}
