package com.elmeftouhi.expensez.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class TagNotFoundException extends BaseException{
    public TagNotFoundException(){
        super("Tag not found");
    }
    public TagNotFoundException(String message) {
        super(message);
    }
}
