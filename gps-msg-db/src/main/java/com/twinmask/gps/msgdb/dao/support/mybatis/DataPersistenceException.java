package com.twinmask.gps.msgdb.dao.support.mybatis;

/**
 * @author Leo
 */
public class DataPersistenceException extends CustomRuntimeException {

    private static final long serialVersionUID = 1L;

    public DataPersistenceException() {
        super();
    }

    public DataPersistenceException(String message) {
        super(message);
    }

    public DataPersistenceException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataPersistenceException(Throwable cause) {
        super(cause);
    }
}
