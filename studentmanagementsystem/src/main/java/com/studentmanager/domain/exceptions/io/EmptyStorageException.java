package com.studentmanager.domain.exceptions.io;

import java.io.IOException;

public class EmptyStorageException extends IOException {
    public EmptyStorageException(String message) {
        super(message);
    }
}
