package pl.piwowarski.facebookly.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

@NoArgsConstructor
@Getter
public class NoPostWithSuchIdException extends RuntimeException{

    @Value("${facebookly.exception.NoPostWithSuchIdException.message-1}")
    private String message1;
}
