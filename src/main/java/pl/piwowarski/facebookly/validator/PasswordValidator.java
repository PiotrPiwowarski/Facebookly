package pl.piwowarski.facebookly.validator;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pl.piwowarski.facebookly.exception.PasswordWrongLengthException;
import pl.piwowarski.facebookly.exception.PasswordWrongSyntaxException;

import java.util.Arrays;

@Service
public class PasswordValidator {

    @Value("${facebookly.password.length.min}")
    private Integer minLength;
    @Value("${facebookly.password.length.max}")
    private Integer maxLength;
    @Value("${facebookly.password.syntax}")
    private String syntax;

    public void validate(String password) {
        if(!validateLength(password.length())){
            throw new PasswordWrongLengthException();
        }
        if(!validateSyntax(password)){
            throw new PasswordWrongSyntaxException();
        }
    }

    private boolean validateLength(int length){
        if(minLength > maxLength){
            throw new IllegalStateException("Właściwość minLength z klasy PasswordValidator jest większe od maxLength");
        }
        return length >= minLength && length <= maxLength;
    }

    private boolean validateSyntax(String password){
        return Arrays
                .stream(password.split(""))
                .allMatch(character -> character.matches("[" + syntax + "]"));
    }
}
