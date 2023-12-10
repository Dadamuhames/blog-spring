package com.example.demo.validators.annotations;


import com.example.demo.validators.PhoneNumberValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PhoneNumberValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface PhoneNumberConstraint {
    String message() default "Phone number should be numeric";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
