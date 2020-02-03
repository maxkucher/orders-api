package com.maxkucher.treezproblem.controllers;


import com.maxkucher.treezproblem.dto.Response;
import com.maxkucher.treezproblem.exceptions.TreezProblemException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApiControllerAdvice {

    @ExceptionHandler({TreezProblemException.class})
    public ResponseEntity<?> handleException(TreezProblemException e) {
        return ResponseEntity.status(e.getStatus()).body(new Response(e.getMessage()));
    }
}
