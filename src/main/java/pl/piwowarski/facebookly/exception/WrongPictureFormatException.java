package pl.piwowarski.facebookly.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class WrongPictureFormatException extends RuntimeException{
    public static final String MESSAGE = "Aplikacja nie obsługuje podanego formatu";
    private String message;
}
