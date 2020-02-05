package com.studentmanager.domain.repositories;

import com.studentmanager.domain.db.JpaDataStore;
import com.studentmanager.domain.models.Teacher;

class TeacherRepository extends CrudRepository<Teacher, Long> {

    private TeacherRepository() {
        /* Leave only one of the lines below uncommented,
        according to your choice of data storage type.*/
        super(JpaDataStore.create(Teacher.class));
//        super(CollectionDataStore.create());
    }

    static TeacherRepository create() {
        return new TeacherRepository();
    }
}