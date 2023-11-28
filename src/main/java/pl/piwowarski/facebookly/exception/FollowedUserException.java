package pl.piwowarski.facebookly.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FollowedUserException extends RuntimeException{

    private static final String MESSAGE = "This user already exist as your friend.";
    private final String message;

    public FollowedUserException(){
        this.message = MESSAGE;
    }
}
