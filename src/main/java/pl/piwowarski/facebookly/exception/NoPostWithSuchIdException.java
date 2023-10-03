package pl.piwowarski.facebookly.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class NoPostWithSuchIdException extends RuntimeException{
    public static final String MESSAGE = "Brak postów o podanym id";
    private String message;
}
