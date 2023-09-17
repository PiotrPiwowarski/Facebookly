package pl.piwowarski.facebookly.controller.exceptionHandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pl.piwowarski.facebookly.exception.NoPostWithSuchId;
import pl.piwowarski.facebookly.exception.NoUserWithSuchId;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(NoPostWithSuchId.class)
    public ResponseEntity<?> handle(NoPostWithSuchId noPostWithSuchId){
        return new ResponseEntity<>(noPostWithSuchId.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NoUserWithSuchId.class)
    public ResponseEntity<?> handle(NoUserWithSuchId noUserWithSuchId){
        return new ResponseEntity<>(noUserWithSuchId.getMessage(), HttpStatus.NOT_FOUND);
    }
}
