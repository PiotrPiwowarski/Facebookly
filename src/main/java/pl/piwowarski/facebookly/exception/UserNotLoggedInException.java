package pl.piwowarski.facebookly.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserNotLoggedInException extends RuntimeException {

    private static final String MESSAGE = "User not logged in.";
    private final String message;

    public UserNotLoggedInException(){
        this.message = MESSAGE;
    }
}
