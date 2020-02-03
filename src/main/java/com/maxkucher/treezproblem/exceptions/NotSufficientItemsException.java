package com.maxkucher.treezproblem.exceptions;

import org.springframework.http.HttpStatus;

public class NotSufficientItemsException extends TreezProblemException {
    private static final String MESSAGE = "Not sufficient items: %d";

    public NotSufficientItemsException(long id) {
        super(String.format(MESSAGE, id), HttpStatus.BAD_REQUEST);
    }
}
