package pl.piwowarski.facebookly.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserIdIsNullException extends RuntimeException{

    private static final String MESSAGE = "User id is null.";
    private final String message;

    public UserIdIsNullException(){
        this.message = MESSAGE;
    }
}
