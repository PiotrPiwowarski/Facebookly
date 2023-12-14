package pl.piwowarski.facebookly.exceptionHandler;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pl.piwowarski.facebookly.exception.*;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<String> handle(AccessDeniedException exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(FollowedUserException.class)
    public ResponseEntity<String> handle(FollowedUserException exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ImageSavingProcessException.class)
    public ResponseEntity<String> handle(ImageSavingProcessException exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NoCommentWithSuchIdException.class)
    public ResponseEntity<String> handle(NoCommentWithSuchIdException exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NoPostWithSuchIdException.class)
    public ResponseEntity<String> handle(NoPostWithSuchIdException exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NoUserWithSuchIdException.class)
    public ResponseEntity<String> handle(NoUserWithSuchIdException exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PasswordWrongLengthException.class)
    public ResponseEntity<String> handle(PasswordWrongLengthException exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PasswordWrongSyntaxException.class)
    public ResponseEntity<String> handle(PasswordWrongSyntaxException exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TryToFollowYourselfException.class)
    public ResponseEntity<String> handle(TryToFollowYourselfException exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserWithSuchEmailAlreadyExistsException.class)
    public ResponseEntity<String> handle(UserWithSuchEmailAlreadyExistsException exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(WrongImageFormatException.class)
    public ResponseEntity<String> handle(WrongImageFormatException exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> handle(ConstraintViolationException exception){
        return new ResponseEntity<>(exception.getConstraintViolations().toString(),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handle(Exception exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Error.class)
    public ResponseEntity<String> handle(Error error){
        return new ResponseEntity<>(error.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
