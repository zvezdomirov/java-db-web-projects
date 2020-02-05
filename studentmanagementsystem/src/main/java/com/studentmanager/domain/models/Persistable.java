package com.studentmanager.domain.models;

/**
 * Interface, that classes should implement,
 * so their objects can be persisted.
 *
 * @param <ID> - Type of the persisted object's id.
 */
public interface Persistable<ID> {
    ID getId();
}
