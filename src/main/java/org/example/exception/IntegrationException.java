package org.example.exception;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.example.dto.error.ErrorDTO;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class IntegrationException extends RuntimeException {

    ErrorDTO error;

    public IntegrationException(ErrorDTO error, String message) {
        super(message);
        this.error = error;
    }
}
