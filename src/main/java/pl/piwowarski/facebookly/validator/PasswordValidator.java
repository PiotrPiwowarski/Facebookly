package pl.piwowarski.facebookly.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<Password, String> {
    private static final Integer MIN_SIZE = 8;
    private static final Integer MAX_SIZE = 255;
    private static final String NUMBERS = ".*\\d.*";
    private static final String SPECIAL_CHARACTERS = ".*[!@#$%^~&*()<>?,./:;|{\\[}\\]_+'\"].*";
    private static final String LETTERS = ".*[a-z].*";
    @Override
    public boolean isValid(String password, ConstraintValidatorContext constraintValidatorContext) {
        if(password.length() < MIN_SIZE || password.length() > MAX_SIZE){
            return false;
        }
        if(!password.matches(NUMBERS)){
            return false;
        }
        if(!password.matches(SPECIAL_CHARACTERS)){
            return false;
        }
        return password.matches(LETTERS);
    }
}
