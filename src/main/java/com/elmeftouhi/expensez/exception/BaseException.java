package com.elmeftouhi.expensez.exception;

import org.springframework.http.HttpStatus;
import org.apache.commons.lang3.ArrayUtils;

public class BaseException extends RuntimeException{

    private final String[] params;
    private HttpStatus status = HttpStatus.BAD_REQUEST;

    protected BaseException(String message){
        super(message);
        this.params = ArrayUtils.EMPTY_STRING_ARRAY;
    }

    protected BaseException(String message, String... params){
        super(message);
        this.params = params.clone();
    }

    protected BaseException(String message, Throwable cause, HttpStatus status){
        super(message, cause);
        this.status = status;
        this.params = ArrayUtils.EMPTY_STRING_ARRAY;
    }

    public String[] getParams(){
        return params;
    }

    public HttpStatus getStatus(){
        return status;
    }

}
