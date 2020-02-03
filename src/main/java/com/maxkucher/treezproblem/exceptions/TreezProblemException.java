package com.maxkucher.treezproblem.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;


@Getter
public class TreezProblemException extends RuntimeException {
    private final String message;
    private final HttpStatus status;

    public TreezProblemException(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }

    public TreezProblemException(String message) {
        this.message = message;
        this.status = HttpStatus.INTERNAL_SERVER_ERROR;
    }
}
