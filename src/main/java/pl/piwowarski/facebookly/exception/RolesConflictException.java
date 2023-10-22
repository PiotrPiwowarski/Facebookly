package pl.piwowarski.facebookly.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class RolesConflictException extends RuntimeException{
    public static final String MESSAGE = "Rola z sesji nie pokrywa się z rolą użytkownika";
    private String message;
}
