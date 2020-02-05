package com.studentmanager.domain.enums;

import com.studentmanager.domain.exceptions.runtime.NoSuchDegreeIdException;

/**
 * Enumeration with the available teacher degrees.
 */
public enum Degree {
    MSc(0),
    BSc(1),
    PHD(2);
    private int id;

    /**
     * @param id - a number value, corresponding to the degree.
     */
    Degree(int id) {
        this.id = id;
    }

    /**
     * @param id - Degree's id.
     * @return The Degree, corresponding to the id argument.
     * @throws NoSuchDegreeIdException if there is no degree with this id.
     */
    public static Degree getById(int id)
            throws NoSuchDegreeIdException {
        for (Degree degree : values()) {
            if (degree.id == id) {
                return degree;
            }
        }
        throw new NoSuchDegreeIdException(
                String.format("No Degree with ID: %d is present.",
                        id)
        );
    }

}
