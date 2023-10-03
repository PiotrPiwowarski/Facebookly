package pl.piwowarski.facebookly.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class NoCommentWithSuchIdException extends RuntimeException {
    public static final String MESSAGE = "Brak komentarzy o podanym id";
    private String message;
}
