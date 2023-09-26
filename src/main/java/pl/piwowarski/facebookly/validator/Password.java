package pl.piwowarski.facebookly.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Target(FIELD)
@Retention(value = RUNTIME)
@Constraint(validatedBy = PasswordValidator.class)
public @interface Password {
    String message() default "Hasło powinno się składać z co najmniej 8 znaków, cyfr i liter";
    Class<?> [] groups() default {};
    Class<? extends Payload> [] payload() default {};
}
