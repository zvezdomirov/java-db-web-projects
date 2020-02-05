package com.studentmanager.domain.repositories;

import com.studentmanager.domain.db.JpaDataStore;
import com.studentmanager.domain.models.Student;

class StudentRepository extends CrudRepository<Student, Long> {

    private StudentRepository() {
        /* Leave only one of the lines below uncommented,
        according to your choice of data storage type.*/
        super(JpaDataStore.create(Student.class));
//        super(CollectionDataStore.create());
    }

    static StudentRepository create() {
        return new StudentRepository();
    }
}
