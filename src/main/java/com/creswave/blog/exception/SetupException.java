package com.creswave.blog.exception;

public class SetupException extends Exception{

    private static final long serialVersionUID = 1L;
    private int errorCode;

    public final int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(final int errorCode) {
        this.errorCode = errorCode;
    }

    public SetupException(String message, int errorCode){
        super(message);
        this.errorCode = errorCode;
    }

    public SetupException(){
        super();
    }

    public SetupException(String message){
        super(message);
    }

    public SetupException(Exception e){
        super(e);
    }

}
