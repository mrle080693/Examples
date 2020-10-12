package com.foxminded.universitytimetable.exceptions;

public class ValidationException extends RuntimeException {
    private String validationExceptionMessage = null;

    public ValidationException(String validationExceptionMessage) {
        this.validationExceptionMessage = validationExceptionMessage;
    }

    public String getValidationExceptionMessage() {
        return validationExceptionMessage;
    }

    public void setValidationExceptionMessage(String validationExceptionMessage) {
        this.validationExceptionMessage = validationExceptionMessage;
    }
}
