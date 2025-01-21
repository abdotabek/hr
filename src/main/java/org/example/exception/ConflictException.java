package org.example.exception;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.example.dto.error.ErrorDTO;

@Getter
@Setter
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ConflictException extends RuntimeException {

    ErrorDTO errorDTO;

    public ConflictException(ErrorDTO error, String message) {
        super(message);
        this.errorDTO = error;
    }
}
