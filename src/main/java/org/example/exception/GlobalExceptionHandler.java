package org.example.exception;

import org.example.dto.error.ErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IntegrationException.class)
    public ResponseEntity<ErrorDTO> handlerIntegrationException(IntegrationException ex) {
        return new ResponseEntity<>(ex.getError(), ex.getError().getStatus());
    }

    @ExceptionHandler(NotFoundExceptions.class)
    public ResponseEntity<ErrorDTO> handlerNotFoundException(NotFoundExceptions ex) {
        return new ResponseEntity<>(ex.getErrorDTO(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ErrorDTO> handlerConflictException(ConflictException ex) {
        return new ResponseEntity<>(ex.getErrorDTO(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(CustomIllegalArgumentException.class)
    public ResponseEntity<ErrorDTO> handlerBadRequestException(CustomIllegalArgumentException ex) {
        return new ResponseEntity<>(ex.getErrorDTO(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserBlockedException.class)
    public ResponseEntity<ErrorDTO> handlerUserBlockedException(UserBlockedException ex) {
        return new ResponseEntity<>(ex.getErrorDTO(), HttpStatus.FORBIDDEN);
    }
}
