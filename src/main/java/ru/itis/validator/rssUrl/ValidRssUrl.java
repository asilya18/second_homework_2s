package ru.itis.validator.rssUrl;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Constraint(validatedBy = RssUrlValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidRssUrl {
    String message() default "некорректная RSS-ссылка";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}