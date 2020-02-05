package com.studentmanager.domain.exceptions.io;

import java.io.IOException;

public class EntityNotFoundException extends IOException {
    public EntityNotFoundException(String message) {
        super(message);
    }
}
