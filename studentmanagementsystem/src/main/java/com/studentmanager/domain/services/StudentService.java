package com.studentmanager.domain.services;

import com.studentmanager.domain.models.Student;
import com.studentmanager.domain.repositories.Repository;
import com.studentmanager.domain.repositories.RepositoryFactory;

public class StudentService extends CrudService<Student, Long> {

    // TODO: Ask if it should be public or private, since it's only used for unit testing.
    public StudentService(Repository<Student, Long> studentRepository) {
        super(studentRepository);
    }

    /**
     * A static factory method, used for instantiation.
     *
     * @return the new instance
     */
    public static StudentService create() {
        return new StudentService(RepositoryFactory.getInstance().getStudentRepository());
    }
}
