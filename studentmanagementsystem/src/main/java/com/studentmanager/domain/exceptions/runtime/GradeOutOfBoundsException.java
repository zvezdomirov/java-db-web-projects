package com.studentmanager.domain.exceptions.runtime;

public class GradeOutOfBoundsException extends RuntimeException {
    public GradeOutOfBoundsException(String message) {
        super(message);
    }
}
