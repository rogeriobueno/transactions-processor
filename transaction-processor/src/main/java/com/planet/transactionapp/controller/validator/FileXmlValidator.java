package com.planet.transactionapp.controller.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

public class FileXmlValidator implements ConstraintValidator<ValidXmlFile, MultipartFile> {
    @Override
    public void initialize(ValidXmlFile constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(MultipartFile value, ConstraintValidatorContext context) {
        String contentType = value.getContentType();
        assert contentType != null;
        return contentType.equals("application/xml") || contentType.equals("text/xml");
    }
}
