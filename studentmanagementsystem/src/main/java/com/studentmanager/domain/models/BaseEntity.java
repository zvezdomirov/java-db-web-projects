package com.studentmanager.domain.models;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * A base class for persisted entities,
 * which have an id of type Long.
 */
@MappedSuperclass
public abstract class BaseEntity implements Persistable<Long> {
    private static long generatedId = 0;
    private long id;

    public BaseEntity() {
        this.setId(generatedId++);
    }

    @Id
    @Column(name = "id")
    @Override
    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
