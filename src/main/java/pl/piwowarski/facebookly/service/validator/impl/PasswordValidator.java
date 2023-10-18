package pl.piwowarski.facebookly.service.validator.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pl.piwowarski.facebookly.exception.PasswordWrongLengthException;
import pl.piwowarski.facebookly.exception.PasswordWrongSyntaxException;
import pl.piwowarski.facebookly.service.validator.Validator;

import java.util.Arrays;

@Service
public class PasswordValidator implements Validator<String> {

    @Value("${facebookly.password.length.min}")
    private Integer minLength;
    @Value("${facebookly.password.length.max}")
    private Integer maxLength;
    @Value("${facebookly.password.syntax}")
    private String syntax;

    @Override
    public void validate(String password) {
        if(!validateLength(password.length())){
            throw new PasswordWrongLengthException(PasswordWrongLengthException.MESSAGE);
        }
        if(!validateSyntax(password)){
            throw new PasswordWrongSyntaxException(PasswordWrongSyntaxException.MESSAGE);
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
