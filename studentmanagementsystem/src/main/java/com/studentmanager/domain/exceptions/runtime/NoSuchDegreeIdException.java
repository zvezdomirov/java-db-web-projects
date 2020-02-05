package com.studentmanager.domain.exceptions.runtime;

public class NoSuchDegreeIdException extends IllegalArgumentException {
    public NoSuchDegreeIdException(String message) {
        super(message);
    }
}
