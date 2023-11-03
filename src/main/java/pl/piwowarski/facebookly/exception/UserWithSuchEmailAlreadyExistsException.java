package pl.piwowarski.facebookly.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserWithSuchEmailAlreadyExistsException extends RuntimeException{

    private static final String MESSAGE = "User with given email already exists. Choose another email.";
    private final String message;

    public UserWithSuchEmailAlreadyExistsException(){
        this.message = MESSAGE;
    }
}
