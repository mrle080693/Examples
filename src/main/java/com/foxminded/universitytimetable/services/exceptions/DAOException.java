package com.foxminded.universitytimetable.services.exceptions;

import org.springframework.dao.DataAccessException;

public class DAOException extends RuntimeException {
    private DataAccessException dataAccessException = null;
    private String DAOExceptionMessage = null;

    public DAOException(String DAOExceptionMessage, DataAccessException dataAccessException) {
        this.DAOExceptionMessage = DAOExceptionMessage;
        this.dataAccessException = dataAccessException;
    }

    public DataAccessException getDataAccessException() {
        return dataAccessException;
    }

    public void setDataAccessException(DataAccessException dataAccessException) {
        this.dataAccessException = dataAccessException;
    }

    public String getDAOExceptionMessage() {
        return DAOExceptionMessage;
    }

    public void setDAOExceptionMessage(String DAOExceptionMessage) {
        this.DAOExceptionMessage = DAOExceptionMessage;
    }
}
