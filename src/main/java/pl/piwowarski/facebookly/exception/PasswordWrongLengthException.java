package pl.piwowarski.facebookly.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

@NoArgsConstructor
@Getter
public class PasswordWrongLengthException extends RuntimeException{

    @Value("${facebookly.exception.PasswordWrongLengthException.message-1}")
    private String message1;
}
