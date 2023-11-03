package pl.piwowarski.facebookly.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AuthorizationException extends RuntimeException{

    private static final String MESSAGE = "Lack of permission to perform the operation - access denied.";
    private final String message;

    public AuthorizationException(){
        this.message = MESSAGE;
    }
}
