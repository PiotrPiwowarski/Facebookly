package pl.piwowarski.facebookly.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class TokenIsNullException extends RuntimeException{
    public static final String MESSAGE = "Token jest null";
    private String message;
}
