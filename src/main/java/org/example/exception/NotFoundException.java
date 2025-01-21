package org.example.exception;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;


@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class NotFoundException extends RuntimeException {

    public NotFoundException(String message) {
        super(message);
    }
}
