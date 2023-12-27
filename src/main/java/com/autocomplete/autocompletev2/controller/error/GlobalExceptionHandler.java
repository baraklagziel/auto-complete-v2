package com.autocomplete.autocompletev2.controller.error;

import com.autocomplete.autocompletev2.controller.AutoCompleteController;
import com.autocomplete.autocompletev2.exceptions.InvalidNameException;
import com.autocomplete.autocompletev2.exceptions.NameNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LogManager.getLogger(AutoCompleteController.class);

    @ExceptionHandler(NameNotFoundException.class)
    public ResponseEntity<String> handleNameNotFound(NameNotFoundException ex) {
        logger.error("Error: ", ex);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(InvalidNameException.class)
    public ResponseEntity<String> handleInvalidName(InvalidNameException ex) {
        logger.error("Error: ", ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneralException(Exception ex) {
        logger.error("Error: ", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + ex.getMessage());
    }
}
