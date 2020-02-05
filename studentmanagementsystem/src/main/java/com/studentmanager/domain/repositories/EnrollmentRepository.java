package com.studentmanager.domain.repositories;

import com.studentmanager.domain.db.JpaDataStore;
import com.studentmanager.domain.models.Enrollment;

class EnrollmentRepository extends CrudRepository<Enrollment, Long> {

    private EnrollmentRepository() {
        /* Leave only one of the lines below uncommented,
        according to your choice of data storage type.*/
        super(JpaDataStore.create(Enrollment.class));
//        super(CollectionDataStore.create());
    }

    static EnrollmentRepository create() {
        return new EnrollmentRepository();
    }
}
