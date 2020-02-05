package com.studentmanager.domain.repositories;

import com.studentmanager.domain.db.JpaDataStore;
import com.studentmanager.domain.models.Course;

class CourseRepository extends CrudRepository<Course, Long> {

    private CourseRepository() {
        /* Leave only one of the lines below uncommented,
        according to your choice of data storage type.*/
        super(JpaDataStore.create(Course.class));
//        super(CollectionDataStore.create());
    }

    static CourseRepository create() {
        return new CourseRepository();
    }
}
