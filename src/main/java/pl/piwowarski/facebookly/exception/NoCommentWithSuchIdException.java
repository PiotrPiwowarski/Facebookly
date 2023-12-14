package pl.piwowarski.facebookly.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class NoCommentWithSuchIdException extends RuntimeException {

    private static final String MESSAGE = "No comments with the given id.";
    private final String message;

    public NoCommentWithSuchIdException() {
        this.message = MESSAGE;
    }
}
