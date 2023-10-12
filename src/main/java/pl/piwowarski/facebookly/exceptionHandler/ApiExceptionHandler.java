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

    @ExceptionHandler(WrongEmailException.class)
    public ResponseEntity<String> handle(WrongEmailException wrongEmailException){
        return new ResponseEntity<>(wrongEmailException.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(WrongPasswordException.class)
    public ResponseEntity<String> handle(WrongPasswordException wrongPasswordException){
        return new ResponseEntity<>(wrongPasswordException.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ExpiredSessionException.class)
    public ResponseEntity<String> handle(ExpiredSessionException expiredSessionException){
        return new ResponseEntity<>(expiredSessionException.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserNotLoggedInException.class)
    public ResponseEntity<String> handle(UserNotLoggedInException userNotLoggedInException){
        return new ResponseEntity<>(userNotLoggedInException.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(TokenIsNullException.class)
    public ResponseEntity<String> handle(TokenIsNullException tokenIsNullException){
        return new ResponseEntity<>(tokenIsNullException.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(UserIdIsNullException.class)
    public ResponseEntity<String> handle(UserIdIsNullException userIsNullException){
        return new ResponseEntity<>(userIsNullException.getMessage(), HttpStatus.UNAUTHORIZED);
    }
}
