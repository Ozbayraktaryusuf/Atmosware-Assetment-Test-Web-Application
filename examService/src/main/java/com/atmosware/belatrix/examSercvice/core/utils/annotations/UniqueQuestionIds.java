package com.atmosware.belatrix.examSercvice.core.utils.annotations;

import com.atmosware.belatrix.examSercvice.core.utils.UniqueQuestionIdValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = UniqueQuestionIdValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueQuestionIds {
    String message() default "Question IDs must be unique.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
