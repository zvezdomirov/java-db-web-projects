package com.studentmanager.domain.repositories;

import com.studentmanager.domain.exceptions.io.EmptyStorageException;
import com.studentmanager.domain.exceptions.io.EntityNotFoundException;

import java.util.stream.Stream;

/**
 * An interface for all repositories,
 * that perform the basic CRUD operations.
 *
 * @param <T>  - Type of persisted entities.
 * @param <ID> - Type of the persisted entities' id.
 */
public interface Repository<T, ID> {
    T save(T obj);

    T findById(ID id) throws EntityNotFoundException;

    Stream<T> findAll() throws EmptyStorageException;

    T update(T obj);

    void delete(T obj);
}
