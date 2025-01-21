package org.example.exception;


import org.example.dto.error.ErrorDTO;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public class ExceptionUtil {
    public static NotFoundExceptions throwNotFoundException(String message) {

        ErrorDTO errorDTO = ErrorDTO
                .builder()
                .title("Resource Not Found")
                .message(message)
                .status(HttpStatus.NOT_FOUND)
                .localDateTime(LocalDateTime.now())
                .build();
        throw new NotFoundExceptions(errorDTO, message);
    }

    public static ConflictException throwConflictException(String message) {

        ErrorDTO errorDTO = ErrorDTO.builder()
                .title("Conflict Error")
                .message(message)
                .status(HttpStatus.CONFLICT)
                .localDateTime(LocalDateTime.now())
                .build();
        throw new ConflictException(errorDTO, message);
    }

    public static IllegalArgumentException throwCustomIllegalArgumentException(String message) {

        ErrorDTO errorDTO = ErrorDTO.builder()
                .title("Bad Request")
                .message(message)
                .status(HttpStatus.BAD_REQUEST)
                .localDateTime(LocalDateTime.now())
                .build();

        throw new CustomIllegalArgumentException(errorDTO, message);
    }

    public static UserBlockedException throwUserBlockedException(String message) {

        ErrorDTO errorDTO = ErrorDTO.builder()
                .title("Access Denied")
                .message(message)
                .status(HttpStatus.FORBIDDEN)
                .localDateTime(LocalDateTime.now())
                .build();
        throw new UserBlockedException(errorDTO, message);
    }

    public static EmployeeNotFoundException throwEmployeeNotFoundException(String message) {

        ErrorDTO errorDTO = ErrorDTO.builder()
                .title("Access Denied")
                .message(message)
                .status(HttpStatus.NOT_FOUND)
                .localDateTime(LocalDateTime.now())
                .build();
        throw new EmployeeNotFoundException(errorDTO, message);
    }

}
