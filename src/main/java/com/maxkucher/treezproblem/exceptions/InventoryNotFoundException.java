package com.maxkucher.treezproblem.exceptions;

import org.springframework.http.HttpStatus;

public class InventoryNotFoundException extends TreezProblemException {
    private static final String MESSAGE_FORMAT = "Inventory with id %d not found";

    public InventoryNotFoundException(long id) {
        super(String.format(MESSAGE_FORMAT, id), HttpStatus.NOT_FOUND);
    }
}

