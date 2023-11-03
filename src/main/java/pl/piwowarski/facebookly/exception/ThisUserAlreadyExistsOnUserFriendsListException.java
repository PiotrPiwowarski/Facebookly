package pl.piwowarski.facebookly.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ThisUserAlreadyExistsOnUserFriendsListException extends RuntimeException{

    private static final String MESSAGE = "This user already exist as your friend.";
    private final String message;

    public ThisUserAlreadyExistsOnUserFriendsListException(){
        this.message = MESSAGE;
    }
}
