package com.assignment.studioservice.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.IOException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ControllerAdvice
public class VolunteerServiceExceptionHandler {

    static Logger logger = LoggerFactory.getLogger(VolunteerServiceExceptionHandler.class);

    @ExceptionHandler(value = IOException.class)
    @ResponseStatus(BAD_REQUEST)
    public ResponseEntity<Object> exception(IOException exception) {
        List<String> errors = List.of(exception.getMessage());
        return returnFormattedError(errors, BAD_REQUEST);
    }

    @ExceptionHandler(value = BadImportException.class)
    @ResponseStatus(BAD_REQUEST)
    public ResponseEntity<Object> exception(BadImportException exception) {
        List<String> errors = List.of("Bad file : " + exception.getMessage());
        return returnFormattedError(errors, BAD_REQUEST);
    }

    private ResponseEntity<Object> returnFormattedError(List<String> errors, HttpStatus status) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", new Date());
        body.put("errors", errors);
        logger.debug("Error in request: {}", errors);
        return new ResponseEntity<>(body, status);
    }
}
