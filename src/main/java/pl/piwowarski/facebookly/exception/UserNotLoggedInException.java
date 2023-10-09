package pl.piwowarski.facebookly.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UserNotLoggedInException extends RuntimeException {
    public static final String MESSAGE = "Odmowa dostępu. Użytkownik nie zalogowany";
    private String message;
}
