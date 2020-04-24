package com.foxminded.universitytimetable.exceptions;

import org.springframework.dao.EmptyResultDataAccessException;

public class NotFoundEntityException extends RuntimeException {
    private EmptyResultDataAccessException emptyResultDataAccessException = null;
    private String emptyResultExceptionMessage = null;

    public NotFoundEntityException(String emptyResultExceptionMessage,
                                   EmptyResultDataAccessException emptyResultDataAccessException) {
        this.emptyResultExceptionMessage = emptyResultExceptionMessage;
        this.emptyResultDataAccessException = emptyResultDataAccessException;
    }

    public EmptyResultDataAccessException getEmptyResultDataAccessException() {
        return emptyResultDataAccessException;
    }

    public void setEmptyResultDataAccessException(EmptyResultDataAccessException emptyResultDataAccessException) {
        this.emptyResultDataAccessException = emptyResultDataAccessException;
    }

    public String getEmptyResultExceptionMessage() {
        return emptyResultExceptionMessage;
    }

    public void setEmptyResultExceptionMessage(String emptyResultExceptionMessage) {
        this.emptyResultExceptionMessage = emptyResultExceptionMessage;
    }
}
