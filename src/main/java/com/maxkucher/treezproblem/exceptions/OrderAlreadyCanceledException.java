package com.maxkucher.treezproblem.exceptions;

import org.springframework.http.HttpStatus;

public class OrderAlreadyCanceledException extends TreezProblemException {
    private static final String MESSAGE = "Order with productId %d was already cancelled";

    public OrderAlreadyCanceledException(long id) {
        super(String.format(MESSAGE, id), HttpStatus.BAD_REQUEST);
    }
}
