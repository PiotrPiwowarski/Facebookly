package pl.piwowarski.facebookly.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ImageSavingProcessException extends RuntimeException{

    private static final String MESSAGE = "Image saving error, check if the provided address is correct.";
    private final String message;

    public ImageSavingProcessException(){
        this.message = MESSAGE;
    }
}
