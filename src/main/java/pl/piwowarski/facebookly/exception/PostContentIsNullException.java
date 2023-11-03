package pl.piwowarski.facebookly.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostContentIsNullException extends RuntimeException{

    private static final String MESSAGE = "Cannot update post, content is null.";
    private final String message;

    public PostContentIsNullException(){
        this.message = MESSAGE;
    }
}
