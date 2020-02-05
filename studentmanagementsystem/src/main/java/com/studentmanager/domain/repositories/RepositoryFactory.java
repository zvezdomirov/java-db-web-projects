package com.studentmanager.domain.repositories;

import com.studentmanager.domain.models.Course;
import com.studentmanager.domain.models.Enrollment;
import com.studentmanager.domain.models.Student;
import com.studentmanager.domain.models.Teacher;

/**
 * A singleton factory, containing getter methods
 * for obtaining the instances of the
 * different repositories.
 */
public class RepositoryFactory {
    private static RepositoryFactory instance;
    private Repository<Course, Long> courseRepository;
    private Repository<Enrollment, Long> enrollmentRepository;
    private Repository<Student, Long> studentRepository;
    private Repository<Teacher, Long> teacherRepository;

    private RepositoryFactory() {
        this.courseRepository = CourseRepository.create();
        this.enrollmentRepository = EnrollmentRepository.create();
        this.studentRepository = StudentRepository.create();
        this.teacherRepository = TeacherRepository.create();
    }

    public static RepositoryFactory getInstance() {
        if (null == instance) {
            instance = new RepositoryFactory();
        }
        return instance;
    }

    public Repository<Course, Long> getCourseRepository() {
        return courseRepository;
    }

    public Repository<Enrollment, Long> getEnrollmentRepository() {
        return enrollmentRepository;
    }

    public Repository<Student, Long> getStudentRepository() {
        return studentRepository;
    }

    public Repository<Teacher, Long> getTeacherRepository() {
        return teacherRepository;
    }
}
