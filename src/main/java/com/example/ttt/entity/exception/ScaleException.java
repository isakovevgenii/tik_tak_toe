package com.example.ttt.entity.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ScaleException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ScaleException() {
        super("The game has a smaller scale. Change the move coordinates.");
    }
}
