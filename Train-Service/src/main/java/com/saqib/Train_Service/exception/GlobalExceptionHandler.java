package com.saqib.Train_Service.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(SeatUnavailableException.class)
    public ResponseEntity<String> seatUnavailable(SeatUnavailableException ex) {
        log.warn("Seat booking conflict: {}", ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(ex.getMessage());
    }


    /* ───────────── IllegalArgumentException ───────────── */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgument(IllegalArgumentException ex,
                                                        HttpServletRequest request) {

        String msg = ex.getMessage() != null ? ex.getMessage() : "Bad request";
        /* 404 अगर message में “not found” हो */
        if (msg.toLowerCase().contains("not found")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(msg);
        }
        /* बाक़ी सब 400 */
        return ResponseEntity.badRequest().body(msg);
    }

    /* ───────────── Validation errors (@Valid) ───────────── */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidation(MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();
        for (FieldError fe : ex.getBindingResult().getFieldErrors()) {
            errors.put(fe.getField(), fe.getDefaultMessage());
        }
        return ResponseEntity.badRequest().body(errors);
    }

    /* ───────────── Catch‑all fallback ───────────── */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneric(Exception ex, HttpServletRequest request) {
        ex.printStackTrace();             // या proper logger
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Internal error: " + ex.getMessage());
    }
}

