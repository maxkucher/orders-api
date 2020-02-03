package com.maxkucher.treezproblem.exceptions;

import org.springframework.http.HttpStatus;


public class OrderNotFoundException extends TreezProblemException {
    private static final String MESSAGE_FORMAT = "Order with productId %d not found";

    public OrderNotFoundException(long id) {
        super(String.format(MESSAGE_FORMAT, id), HttpStatus.NOT_FOUND);
    }
}
