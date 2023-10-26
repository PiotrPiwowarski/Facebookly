package pl.piwowarski.facebookly.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

@NoArgsConstructor
@Getter
public class PictureSavingProcessException extends RuntimeException{

    @Value("${facebookly.exception.PictureSavingProcessException.message-1}")
    private String message1;
}
