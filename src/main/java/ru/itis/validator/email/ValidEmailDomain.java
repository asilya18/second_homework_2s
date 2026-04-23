package ru.itis.validator.email;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = ValidEmailDomainValidator.class)
// аннотация вешается на поле
@Target({ElementType.FIELD})
// аннотация работает все время во время выполнения
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidEmailDomain {
    String message() default "недопустимый домен email";
    String[] allowed();
    // служебные параметры
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
