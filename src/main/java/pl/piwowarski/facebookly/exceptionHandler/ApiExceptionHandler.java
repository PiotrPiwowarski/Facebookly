package pl.piwowarski.facebookly.exceptionHandler;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pl.piwowarski.facebookly.exception.*;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(NoPostWithSuchId.class)
    public ResponseEntity<String> handle(NoPostWithSuchId noPostWithSuchId){
        return new ResponseEntity<>(noPostWithSuchId.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NoUserWithSuchId.class)
    public ResponseEntity<String> handle(NoUserWithSuchId noUserWithSuchId){
        return new ResponseEntity<>(noUserWithSuchId.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NoCommentWithSuchId.class)
    public ResponseEntity<String> handle(NoCommentWithSuchId noCommentWithSuchId){
        return new ResponseEntity<>(noCommentWithSuchId.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CommentContentIsNull.class)
    public ResponseEntity<String> handle(CommentContentIsNull commentIdIsNull){
        return new ResponseEntity<>(commentIdIsNull.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PostContentIsNull.class)
    public ResponseEntity<String> handle(PostContentIsNull postContentIsNull){
        return new ResponseEntity<>(postContentIsNull.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TryingToAddYourselfAsAFriend.class)
    public ResponseEntity<String> handle(TryingToAddYourselfAsAFriend tryingToAddYourselfAsAFriend){
        return new ResponseEntity<>(tryingToAddYourselfAsAFriend.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> handle(ConstraintViolationException constraintViolationException){
        return new ResponseEntity<>(constraintViolationException.getConstraintViolations().toString(),
                HttpStatus.BAD_REQUEST);
    }
}
