package com.example.ttt.entity.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class GameEndingException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public GameEndingException() {
        super("This game is over.");
    }
}
