package pl.piwowarski.facebookly.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UserIdIsNullException extends RuntimeException{
    public static final String MESSAGE = "ID użytkownika jest null";
    private String message;
}
