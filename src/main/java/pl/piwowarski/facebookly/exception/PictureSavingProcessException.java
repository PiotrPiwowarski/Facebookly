package pl.piwowarski.facebookly.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class PictureSavingProcessException extends RuntimeException{
    public static final String MESSAGE = "Błąd zapisu obrazka, sprawdź czy podany adres jest poprawny";
    private String message;
}
