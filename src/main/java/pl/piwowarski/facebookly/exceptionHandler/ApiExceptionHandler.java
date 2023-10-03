package pl.piwowarski.facebookly.exceptionHandler;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pl.piwowarski.facebookly.exception.*;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(NoPostWithSuchIdException.class)
    public ResponseEntity<String> handle(NoPostWithSuchIdException noPostWithSuchIdException){
        return new ResponseEntity<>(noPostWithSuchIdException.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NoUserWithSuchIdException.class)
    public ResponseEntity<String> handle(NoUserWithSuchIdException noUserWithSuchIdException){
        return new ResponseEntity<>(noUserWithSuchIdException.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NoCommentWithSuchIdException.class)
    public ResponseEntity<String> handle(NoCommentWithSuchIdException noCommentWithSuchIdException){
        return new ResponseEntity<>(noCommentWithSuchIdException.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CommentContentIsNullException.class)
    public ResponseEntity<String> handle(CommentContentIsNullException commentIdIsNull){
        return new ResponseEntity<>(commentIdIsNull.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PostContentIsNullException.class)
    public ResponseEntity<String> handle(PostContentIsNullException postContentIsNullException){
        return new ResponseEntity<>(postContentIsNullException.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TryingToAddYourselfAsAFriendException.class)
    public ResponseEntity<String> handle(TryingToAddYourselfAsAFriendException tryingToAddYourselfAsAFriendException){
        return new ResponseEntity<>(tryingToAddYourselfAsAFriendException.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> handle(ConstraintViolationException constraintViolationException){
        return new ResponseEntity<>(constraintViolationException.getConstraintViolations().toString(),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PictureSavingProcessException.class)
    public ResponseEntity<String> handle(PictureSavingProcessException pictureSavingProcessException){
        return new ResponseEntity<>(pictureSavingProcessException.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
