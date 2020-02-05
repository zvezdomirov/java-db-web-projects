package com.studentmanager.domain.exceptions.runtime;

public class NoCourseTeacherException extends RuntimeException {
    public NoCourseTeacherException(String message) {
        super(message);
    }
}
