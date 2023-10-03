package pl.piwowarski.facebookly.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CommentContentIsNullException extends RuntimeException{
    public static final String MESSAGE = "Brak możliwości aktualizacji komentarza, content == null";
    private String message;
}
