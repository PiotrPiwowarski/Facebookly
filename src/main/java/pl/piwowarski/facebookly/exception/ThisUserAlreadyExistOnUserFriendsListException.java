package pl.piwowarski.facebookly.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ThisUserAlreadyExistOnUserFriendsListException extends RuntimeException{
    public static final String MESSAGE = "Ten użytkownik już istnieje na liście twoich znajomych";
    private String message;
}
