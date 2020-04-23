package com.foxminded.universitytimetable.exceptions;

import org.springframework.dao.DataAccessException;

public class DAOException extends RuntimeException {
    private DataAccessException dataAccessException = null;
    private String DAOExceptionMessage = null;

    public DAOException(DataAccessException dataAccessException, String DAOExceptionMessage) {
        this.dataAccessException = dataAccessException;
        this.DAOExceptionMessage = DAOExceptionMessage;
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
