package pl.piwowarski.facebookly.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TryingToAddYourselfAsAFriendException extends RuntimeException {

    private static final String MESSAGE = "You cannot add yourself to your friends.";
    private final String message;

    public TryingToAddYourselfAsAFriendException(){
        this.message = MESSAGE;
    }
}
