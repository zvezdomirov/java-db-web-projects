package com.studentmanager.domain.services;

import com.studentmanager.domain.models.Teacher;
import com.studentmanager.domain.repositories.Repository;
import com.studentmanager.domain.repositories.RepositoryFactory;

public class TeacherService extends CrudService<Teacher, Long> {

    private TeacherService(Repository<Teacher, Long> teacherRepository) {
        super(teacherRepository);
    }

    /**
     * A static factory method, used for instantiation.
     *
     * @return the new instance
     */
    public static TeacherService create() {
        return new TeacherService(
                RepositoryFactory.getInstance().getTeacherRepository());
    }
}
