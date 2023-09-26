package pl.piwowarski.facebookly.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CommentContentIsNull extends RuntimeException{
    public static final String MESSAGE = "Brak możliwości aktualizacji komentarza, content == null";
    private String message;
}
