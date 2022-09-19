package com.example.ttt.entity.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NonexistentGamerException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public NonexistentGamerException() {
        super("This player is not registered in this party. Choose another player.");
    }
}
