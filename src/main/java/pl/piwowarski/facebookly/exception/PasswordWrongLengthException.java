package pl.piwowarski.facebookly.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PasswordWrongLengthException extends RuntimeException{
    public static final String MESSAGE = "Błędna długość hasła";
    private String message;
}
