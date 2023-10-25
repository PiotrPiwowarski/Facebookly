package pl.piwowarski.facebookly.service.validator;

import org.junit.jupiter.api.Test;
import pl.piwowarski.facebookly.service.validator.impl.PasswordValidator;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class PasswordValidatorTest {

    private final Validator<String> passwordValidator = new PasswordValidator();

    @Test
    void validateTest(){
        String password = "abc";
        assertDoesNotThrow(() -> passwordValidator.validate(password));
    }
}
