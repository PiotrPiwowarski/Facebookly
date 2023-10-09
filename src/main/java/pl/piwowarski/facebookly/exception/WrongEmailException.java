package pl.piwowarski.facebookly.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class WrongEmailException extends RuntimeException {
    public static final String MESSAGE = "Brak użytkowników o podanym emailu";
    private String message;
}
