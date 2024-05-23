package com.planet.transactionapp.controller.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {FileXmlValidator.class})
public @interface ValidXmlFile {

    Class<? extends Payload>[] payload() default {};

    Class<?>[] groups() default {};

    String message() default "Only XML files are allowed";
}

