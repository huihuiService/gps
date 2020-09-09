package com.twinmask.gps.msgdb.dao.support.mybatis;

/**
 * @author Leo
 */
public class CustomRuntimeException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private String messageKey;
    private String[] messageArgs;

    public CustomRuntimeException() {
        super();
    }

    public CustomRuntimeException(String message) {
        super(message);
    }

    public CustomRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public CustomRuntimeException(String messageKey, String message) {
        super(message);
        this.messageKey = messageKey;
        this.messageArgs = new String[]{message};
    }

    public CustomRuntimeException(String messageKey, String[] messageArgs,
                                  String message) {
        super(message);
        this.messageKey = messageKey;
        this.messageArgs = messageArgs;
    }

    public CustomRuntimeException(Throwable cause) {
        super(cause);
    }

    public String getMessageKey() {
        return messageKey;
    }

    public String[] getMessageArgs() {
        return messageArgs;
    }
}
