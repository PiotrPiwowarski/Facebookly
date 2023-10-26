package pl.piwowarski.facebookly.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

@NoArgsConstructor
@Getter
public class WrongEmailException extends RuntimeException {

    @Value("${facebookly.exception.WrongEmailException.message-1}")
    private String message1;
}
