package pl.piwowarski.facebookly.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ExpiredSessionException extends RuntimeException {
    public static final String MESSAGE_1 = "Brak aktywnej sesji dla podanego tokenu";
    public static final String MESSAGE_2 = "Sesja wygasła. Zaloguj się ponownie";
    private String message;
}
