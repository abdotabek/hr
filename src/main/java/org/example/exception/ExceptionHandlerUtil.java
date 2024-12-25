package org.example.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class ExceptionHandlerUtil {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<GlobalExceptionResponse> handlerNotFoundException(NotFoundException exception) {
        GlobalExceptionResponse response = GlobalExceptionResponse
                .builder()
                .title("Resource not found")
                .message(exception.getMessage())
                .status(HttpStatus.NOT_FOUND)
                .localDateTime(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
