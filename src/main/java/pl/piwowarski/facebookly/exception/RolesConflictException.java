package pl.piwowarski.facebookly.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RolesConflictException extends RuntimeException{

    private static final String MESSAGE = "The session role isn't equal to user role.";
    private final String message;

    public RolesConflictException(){
        this.message = MESSAGE;
    }
}
