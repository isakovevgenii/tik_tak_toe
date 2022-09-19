package com.example.ttt.entity.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class AccessMoveException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public AccessMoveException() {
        super("This cell for the move is occupied. Change the move coordinates.");
    }
}