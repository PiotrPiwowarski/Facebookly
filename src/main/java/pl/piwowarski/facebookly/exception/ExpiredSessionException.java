package pl.piwowarski.facebookly.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ExpiredSessionException extends RuntimeException {

    private static final String MESSAGE = "Session has expired. Log in again.";
    private final String message;

    public ExpiredSessionException(){
        this.message = MESSAGE;
    }
}
