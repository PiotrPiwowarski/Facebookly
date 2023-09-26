package pl.piwowarski.facebookly.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PostContentIsNull extends RuntimeException{
    public static final String MESSAGE = "Brak możliwości aktualizacji postu, content == null";
    private String message;
}
