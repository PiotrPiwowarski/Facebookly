package pl.piwowarski.facebookly.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommentContentIsNullException extends RuntimeException{

    private static final String MESSAGE = "You cannot update comment, content is null.";
    private final String message;

    public CommentContentIsNullException(){
        this.message = MESSAGE;
    }
}
