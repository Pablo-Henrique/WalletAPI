package com.wallet.wallet.exceptions;

import com.wallet.wallet.exceptions.ex.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;

@RestControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(value = ResourceNotFoundException.class)
    public ResponseEntity<HandlerError> resourceNotFound(ResourceNotFoundException ex, HttpServletRequest request) {
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;
        String error = "Resource not found";
        HandlerError handlerError = new HandlerError(request.getRequestURI(), httpStatus.value(), error, Instant.now(), ex.getMessage());
        return ResponseEntity.status(httpStatus).body(handlerError);
    }

}
