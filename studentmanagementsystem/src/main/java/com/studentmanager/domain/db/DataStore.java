package com.studentmanager.domain.db;

import com.studentmanager.domain.models.Persistable;

import com.studentmanager.domain.models.BaseEntity;
import java.util.stream.Stream;

/**
 * Interface, containing the basic CRUD operations.
 * Classes, implementing this interface, can be used
 * as a data storage for the application, e.g.:
 * - {@link CollectionDataStore} stores the data in-memory, using Collections.
 * - {@link JpaDataStore} stores the data in a database, using JPA.
 *
 * @param <T>  - Stored object type, which should extend the {@link Persistable} interface,
 *             e.g {@link BaseEntity}.
 * @param <ID> - Type of the persisted object's id.
 */
public interface DataStore<T extends Persistable<ID>, ID> {
    T getById(ID id);

    Stream<T> getAll();

    <S extends T> S save(S entity);

    void delete(T entity);
}
