package com.foxminded.universitytimetable.exceptions;

public class ValidationException extends RuntimeException {
    private String entityValidationExceptionMessage = null;

    public ValidationException(String entityValidationExceptionMessage) {
        this.entityValidationExceptionMessage = entityValidationExceptionMessage;
    }

    public String getEntityValidationExceptionMessage() {
        return entityValidationExceptionMessage;
    }

    public void setEntityValidationExceptionMessage(String entityValidationExceptionMessage) {
        this.entityValidationExceptionMessage = entityValidationExceptionMessage;
    }
}
