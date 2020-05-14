package com.foxminded.universitytimetable.exceptions;

public class EntityValidationException extends RuntimeException {
    private String entityValidationExceptionMessage = null;

    public EntityValidationException(String entityValidationExceptionMessage) {
        this.entityValidationExceptionMessage = entityValidationExceptionMessage;
    }

    public String getEntityValidationExceptionMessage() {
        return entityValidationExceptionMessage;
    }

    public void setEntityValidationExceptionMessage(String entityValidationExceptionMessage) {
        this.entityValidationExceptionMessage = entityValidationExceptionMessage;
    }
}
