package com.studentmanager.domain.exceptions.runtime;

public class CourseTeacherAlreadyPresentException extends RuntimeException {
    public CourseTeacherAlreadyPresentException(String message) {
        super(message);
    }
}
