package pl.piwowarski.facebookly.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

@NoArgsConstructor
@Getter
public class PostContentIsNullException extends RuntimeException{

    @Value("${facebookly.exception.PostContentIsNullException.message-1}")
    private String message1;
}
