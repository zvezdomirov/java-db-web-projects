package com.studentmanager.domain.repositories;

import com.studentmanager.domain.db.DataStore;
import com.studentmanager.domain.exceptions.io.EmptyStorageException;
import com.studentmanager.domain.exceptions.io.EntityNotFoundException;
import com.studentmanager.domain.models.Persistable;

import java.util.Optional;
import java.util.stream.Stream;

abstract class CrudRepository<T extends Persistable<ID>, ID>
        implements Repository<T, ID> {
    private DataStore<T, ID> dataStore;

    CrudRepository(DataStore<T, ID> dataStore) {
        this.dataStore = dataStore;
    }

    @Override
    public T save(T t) {
        return this.dataStore.save(t);
    }

    @Override
    public T findById(ID id) throws EntityNotFoundException {
        return Optional.ofNullable(this.dataStore.getById(id))
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format(
                                "Entity with id: %s not found.",
                                id)
                ));
    }

    @Override
    public Stream<T> findAll() throws EmptyStorageException {
        return Optional.ofNullable(this.dataStore.getAll())
                .orElseThrow(() -> new EmptyStorageException(
                        "No entities found."
                ));
    }

    @Override
    public T update(T t) {
        return this.dataStore.save(t);
    }

    @Override
    public void delete(T t) {
        this.dataStore.delete(t);
    }
}
