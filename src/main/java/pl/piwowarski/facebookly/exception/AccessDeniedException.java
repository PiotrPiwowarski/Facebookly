package pl.piwowarski.facebookly.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AccessDeniedException extends RuntimeException{
    public static final String MESSAGE = "Brak uprawnień do wykonania operacji - odmowa dostępu";
    private String message;
}
